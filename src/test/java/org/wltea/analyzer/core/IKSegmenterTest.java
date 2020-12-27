package org.wltea.analyzer.core;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugin.analysis.ik.AnalysisIkPlugin;
import org.junit.Test;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IKSegmenterTest {

    @Test
    public void test() throws IOException {
        Configuration configuration = mock(Configuration.class);
        when(configuration.isEnableLowercase()).thenReturn(true);
        when(configuration.isEnableRemoteDict()).thenReturn(false);
        when(configuration.isUseSmart()).thenReturn(true);
        when(configuration.isKeepPunctuations()).thenReturn(true);
        Environment environment = mock(Environment.class);
        File tempConfig = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        when(environment.configFile()).thenReturn(tempConfig.toPath());
        when(configuration.getEnvironment()).thenReturn(environment);
        try {
            File srcConfig = new File("config");
            FileUtils.copyDirectory(srcConfig, new File(tempConfig, AnalysisIkPlugin.PLUGIN_NAME));
            Dictionary.initial(configuration);
            StringReader reader = new StringReader("你好，;.\n中华人民共和国国歌！,.!?\"':;，。！？“”‘’：；、〔（）[]{}─-．《》〈〉·—_*×□/▲●～…→「」『』﹏\n");
            IKSegmenter segmenter = new IKSegmenter(reader, configuration);
            Lexeme lexeme;
            while ((lexeme = segmenter.next()) != null) {
                System.out.println(lexeme);
            }
            segmenter.reset(new StringReader("\n"));
            while ((lexeme = segmenter.next()) != null) {
                System.out.println(lexeme);
            }
        } finally {
            FileUtils.deleteQuietly(tempConfig);
        }
    }

}