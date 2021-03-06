package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.PrestataireDAL;
import fr.univ_lorraine.oops.library.model.Prestataire;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct; 
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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
    @Inject
    PrestataireDAL pd;
    
    private final Set stopWordsSet = FrenchAnalyzer.getDefaultStopSet();
    private Analyzer analyzer;
    private IndexWriter iwriter;
    private IndexWriterConfig config;
    private Directory directory;    
    
    /**
     * Constructor
     * @throws IOException
     */
    public LuceneBean() throws IOException {
        this.analyzer = new FrenchAnalyzer();
        this.directory = new RAMDirectory();
        this.config = new IndexWriterConfig(analyzer);
        this.iwriter = new IndexWriter(directory, config);
    }

    /**
     * Initialize the variables
     */
    @PostConstruct
    public void init() {
       try {
            //Chargement des prestataires  
            List<Prestataire> liste = pd.getAll();
            for (Prestataire p : liste) {
                iwriter.addDocument(prestataireToDocument(p));
            }
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Index a Prestataire
     * @param p the Prestataire to index
     */
    public void indexPrestataire(Prestataire p) {
        try {
            iwriter.addDocument(prestataireToDocument(p));
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    /**
     * Remove a Prestataire from the index
     * @param p the Prestataire to remove
     */
    public void removePrestataire(Prestataire p) {  
        removePrestataire(p.getLogin());
    }
    
    /**
     * Remove a Prestataire from the index
     * @param login the login of the Prestataire to remove
     */
    public void removePrestataire(String login){
        try {
            iwriter.deleteDocuments(new Term("id",login));
            iwriter.commit();
        } catch (IOException ex) {
            Logger.getLogger(LuceneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Update the Prestataire in the index
     * @param p the Prestataire to update
     */
    public void updatePrestataire(Prestataire p){
        removePrestataire(p);
        indexPrestataire(p);
    }

    /**
     * Return a HashMap of id and percentage of matching between the Prestataire in the index and the result of the search
     * @param quoi the keyword to look for
     * @return a HashMap of id and percentage of matching between the Prestataire in the index and the result of the search
     */
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

}
