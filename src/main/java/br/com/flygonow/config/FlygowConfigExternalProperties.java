package br.com.flygonow.config;

/**
 * @author Tiago Rocha Gomes
 */
public interface FlygowConfigExternalProperties {
    public String getProperty(String name);
    public String getProperty(final String name, final String[] params);
}
