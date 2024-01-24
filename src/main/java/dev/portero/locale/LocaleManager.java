package dev.portero.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LocaleManager {

    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private final Set<Locale> installed = ConcurrentHashMap.newKeySet();
    private TranslationRegistry registry;

    public void reload() {
        if (this.registry != null) {
            GlobalTranslator.translator().removeSource(this.registry);
            this.installed.clear();
        }

        this.registry = TranslationRegistry.create(Key.key("xenon", "main"));
        this.registry.defaultLocale(DEFAULT_LOCALE);

        loadFromResourceBundle(new Locale("es"));
        loadFromResourceBundle(DEFAULT_LOCALE);

        GlobalTranslator.translator().addSource(this.registry);
    }

    private void loadFromResourceBundle(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("neon", locale, UTF8ResourceBundleControl.get());
        try {
            this.registry.registerAll(locale, bundle, false);
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }
}