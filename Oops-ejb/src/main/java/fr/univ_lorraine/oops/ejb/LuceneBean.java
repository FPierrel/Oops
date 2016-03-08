package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct; 
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

@Singleton
@Startup
@LocalBean
public class LuceneBean {
    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;    
    private final Set stopWordsSet = FrenchAnalyzer.getDefaultStopSet();
    private Analyzer analyzer;
    private IndexWriter iwriter;
    private IndexWriterConfig config;
    private Directory directory;

    public LuceneBean() throws IOException {
        this.analyzer = new FrenchAnalyzer();
        this.directory = new RAMDirectory();
        this.config = new IndexWriterConfig(analyzer);
        this.iwriter = new IndexWriter(directory, config);
    }

    @PostConstruct
    public void init() {
       try {
            //Chargement des prestataires  
            List<Prestataire> liste = em.createQuery("SELECT p FROM Prestataire p").getResultList();
            for (Prestataire p : liste) {
                iwriter.addDocument(prestataireToDocument(p));
            }
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void indexPrestataire(Prestataire p) {
        try {
            iwriter.addDocument(prestataireToDocument(p));
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public void removePrestataire(Prestataire p) {  
        removePrestataire(p.getLogin());
    }
    
    public void removePrestataire(String login){
        try {
            iwriter.deleteDocuments(new Term("id",login));
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updatePrestataire(Prestataire p){
        removePrestataire(p);
        indexPrestataire(p);
    }

    public HashMap<String, Float> search(String quoi) {        
        HashMap<String,Float> result = new HashMap<>();
        if (quoi.isEmpty()) {
            return result;
        }
        try {
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            //Ajout des suffix fuzzy pour tenir compte des fautes de frappes
            //TODO: trouver un moyen plus rapide
            while (quoi.contains("  "))
                quoi = quoi.replaceAll("  ", " ");
            quoi = quoi.replaceAll(" ", "~ ");
            quoi = quoi.replaceAll("\t", "~ ");
            quoi += "~";
            Query query;
            QueryParser parser = new MultiFieldQueryParser(new String[]{"description", "categorie", "nom"}, analyzer);
            parser.setFuzzyMinSim(0.6f);
            query = parser.parse(quoi);

            //Recuperation des resultats
            TopDocs hits;
            hits = isearcher.search(query, null, 1000);
            for (ScoreDoc sd : hits.scoreDocs) {
                try {
                    result.put(isearcher.doc(sd.doc).getField("id").stringValue(), sd.score);
                } catch (IOException ex) {
                    Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            ireader.close();            
        } catch (ParseException | IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }   
    
    private Document prestataireToDocument(Prestataire p) {
        Document d = new Document();
        d.add(new Field("id", p.getLogin(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        d.add(new Field("nom", p.getNomEntreprise(), Field.Store.YES, Field.Index.ANALYZED));
        d.add(new Field("categorie", p.getLuceneCategorieDescription(), Field.Store.YES, Field.Index.ANALYZED));
        d.add(new Field("description",p.getDescription(),Field.Store.YES, Field.Index.ANALYZED));
        return d;
    }
    
    @PreDestroy
    public void close(){
        em.close();
    }    
}
