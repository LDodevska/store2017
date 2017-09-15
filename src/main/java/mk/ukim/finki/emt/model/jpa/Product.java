package mk.ukim.finki.emt.model.jpa;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.codehaus.jackson.map.Serializers;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor on 07-Jun-17.
 */
@Entity
@Table(name = "products")
@Indexed
@AnalyzerDef(name = "emtAnalyser",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        })

public class Product extends BaseEntity{
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "emtAnalyser")
    @Boost(2f)
    public String name;

    public Double price;

    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "emtAnalyser")
    @Boost(0.5f)
    public String isbn;

    public Integer quantityInStock;

    @ManyToOne
    @IndexedEmbedded
    public Category category;

    public Boolean promoted;


}
