package br.com.flygonow.config.impl;

import br.com.flygonow.config.FlygowConfigExternalProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FlygowConfigExternalPropertiesImpl extends PropertyPlaceholderConfigurer implements FlygowConfigExternalProperties {

    private static Logger logger = Logger.getLogger(FlygowConfigExternalPropertiesImpl.class);

    private final Map<String, String> propertiesMap = new HashMap<String, String>();

    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactory, final Properties props)
            throws BeansException {

        this.propertiesMap.clear();
        for (final Map.Entry<Object, Object> e: props.entrySet()) {
            this.propertiesMap.put(e.getKey().toString(), e.getValue().toString());
        }

        super.processProperties(beanFactory, props);
    }

    @Override
    protected void doProcessProperties(final ConfigurableListableBeanFactory beanFactoryToProcess,
                                       final StringValueResolver valueResolver) {

        super.doProcessProperties(beanFactoryToProcess, valueResolver);

        for(final Map.Entry<String, String> e: propertiesMap.entrySet()) {
            e.setValue(valueResolver.resolveStringValue(e.getValue()));
        }
    }

    @Override
    protected String resolvePlaceholder(final String placeholder, final Properties props) {
        return super.resolvePlaceholder(placeholder, props);
    }

    public String getProperty(final String name) {
        return propertiesMap.get(name);
    }

    public String getProperty(final String name, final String[] params) {
        return MessageFormat.format(propertiesMap.get(name), params);
    }

    @Override
    protected void loadProperties(final Properties props) throws IOException {
        try{
            super.loadProperties(props);
        }catch (final IOException ioe){
            logger.debug(ioe.getMessage(), ioe);
        }
    }
}