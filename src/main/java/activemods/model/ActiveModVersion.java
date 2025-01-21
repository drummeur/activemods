package activemods.model;

import activemods.ActiveModsConstants;
import com.google.gson.annotations.SerializedName;
import com.vdurmont.semver4j.Semver;

import java.io.Serializable;

public class ActiveModVersion implements Serializable
{
    @SerializedName(ActiveModsConstants.MOD_VERSION_VERSION_KEY)
    public String Version;
    @SerializedName(ActiveModsConstants.MOD_VERSION_MAJOR_KEY)
    public Integer Major;
    @SerializedName(ActiveModsConstants.MOD_VERSION_MINOR_KEY)
    public Integer Minor;
    @SerializedName(ActiveModsConstants.MOD_VERSION_PATCH_KEY)
    public Integer Patch;

    public ActiveModVersion(Semver version)
    {
        this.Version = version.toString();
        this.Major = version.getMajor();
        this.Minor = version.getMinor();
        this.Patch = version.getPatch();
    }
}
