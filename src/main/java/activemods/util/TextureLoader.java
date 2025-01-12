package activemods.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;

import static activemods.Main.*;

public class TextureLoader
{
    private static final HashMap<String, Texture> textures = new HashMap<>();

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     *                 Example: imagePath("missing.png")
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or a "missing image" texture if it doesn't exist.
     */
    public static Texture getTexture(final String filePath)
    {
        return getTexture(filePath, true);
    }

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     *                 Example: imagePath("missing.png")
     * @param linear   - Whether the image should use a linear or nearest filter for scaling.
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or a "missing image" texture if it doesn't exist.
     */
    public static Texture getTexture(final String filePath, boolean linear)
    {
        if (textures.get(filePath) == null)
        {
            try
            {
                loadTexture(filePath, linear);
            }
            catch (GdxRuntimeException e)
            {
                try
                {
                    return getTexture(imagePath("missing.png"), false);
                }
                catch (GdxRuntimeException ex)
                {
                    logger.info("missing.png is missing!");
                    return null;
                }
            }
        }
        Texture t = textures.get(filePath);
        if (t != null && t.getTextureObjectHandle() == 0)
        {
            textures.remove(filePath);
            t = getTexture(filePath, linear);
        }
        return t;
    }


    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException
    {
        Texture texture = new Texture(textureString);
        if (linearFilter)
        {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        else
        {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        logger.info("Loaded texture " + textureString);
        textures.put(textureString, texture);
    }

}
