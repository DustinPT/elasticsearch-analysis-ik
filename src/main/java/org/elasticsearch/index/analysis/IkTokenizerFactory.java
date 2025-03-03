package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.lucene.IKTokenizer;

public class IkTokenizerFactory extends AbstractTokenizerFactory {
  private Configuration configuration;

  public IkTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
      super(indexSettings, settings,name);
	  configuration=new Configuration(env,settings);
  }

  public static IkTokenizerFactory getIkTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
      return new IkTokenizerFactory(indexSettings,env, name, settings).setSmart(false).keepPunctuations(false);
  }

  public static IkTokenizerFactory getIkKpTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
      return new IkTokenizerFactory(indexSettings,env, name, settings).setSmart(false).keepPunctuations(true);
  }

  public static IkTokenizerFactory getIkSmartTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
      return new IkTokenizerFactory(indexSettings,env, name, settings).setSmart(true).keepPunctuations(false);
  }

  public static IkTokenizerFactory getIkSmartKpTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
      return new IkTokenizerFactory(indexSettings, env, name, settings).setSmart(true).keepPunctuations(true);
  }

  public IkTokenizerFactory setSmart(boolean smart){
        this.configuration.setUseSmart(smart);
        return this;
  }

  public IkTokenizerFactory keepPunctuations(boolean keepPunctuations){
      this.configuration.setKeepPunctuations(keepPunctuations);
      return this;
  }

  @Override
  public Tokenizer create() {
      return new IKTokenizer(configuration);  }
}
