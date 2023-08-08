package dev.ebullient.convert.qute;

import java.util.Collection;
import java.util.List;

import dev.ebullient.convert.tools.CompendiumSources;
import dev.ebullient.convert.tools.IndexType;
import dev.ebullient.convert.tools.Tags;
import io.quarkus.qute.TemplateData;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Defines attributes inherited by other Qute templates.
 * <p>
 * Notes created from {@code QuteBase} (or a derivative) will use a specific template
 * for the type. For example, {@code QuteBackground} will use {@code background2md.txt}.
 * </p>
 */
@TemplateData
@RegisterForReflection
public class QuteBase implements QuteUtil {
    protected final String name;
    protected final CompendiumSources sources;
    protected final String sourceText;

    /** Formatted text. For most templates, this is the bulk of the content. */
    public final String text;

    /** Collected tags for inclusion in frontmatter */
    public final Collection<String> tags;

    private String vaultPath;

    public QuteBase(CompendiumSources sources, String name, String source, String text, Tags tags) {
        this.sources = sources;
        this.name = name;
        this.sourceText = source;
        this.text = text;
        this.tags = tags == null ? List.of() : tags.build();
    }

    /** Note name */
    public String getName() {
        return name;
    }

    /** Formatted string describing the content's source(es) */
    public String getSource() {
        return sourceText;
    }

    /** True if the content (text) contains sections */
    public boolean getHasSections() {
        return text.contains("\n## ");
    }

    public void vaultPath(String vaultPath) {
        this.vaultPath = vaultPath;
    }

    /** Path to this note in the vault */
    public String getVaultPath() {
        if (vaultPath != null) {
            return vaultPath;
        }
        return targetPath() + '/' + targetFile();
    }

    public CompendiumSources sources() {
        return sources;
    }

    public String title() {
        return name;
    }

    public String targetFile() {
        return name;
    }

    public String targetPath() {
        return ".";
    }

    public IndexType type() {
        return sources.getType();
    }

    public String key() {
        return sources.getKey();
    }

    public boolean createIndex() {
        return true;
    }

    public String template() {
        IndexType type = type();
        return String.format("%s2md.txt", type.templateName());
    }
}
