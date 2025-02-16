package activemods.ui;

import activemods.ActiveModsMain;
import basemod.*;


public class ActiveModsConfigPanel extends ModPanel
{
    private static float LABEL_X = 475.0f;
    private static float LABEL_Y = 700.0f;

    // main page, with stats (?) and controls to scan and save mods
    // paginated individual pages with rows of modIds and URLs, and a button next to the row to remove it
    // bool checkbox to "use default" for workshop mods?
    // can I make it so that you can paste in here?  if not, totally unusable.

    // do I even want to do this?  it's a lot of tbh annoying code that's still going to end up with a crappy UX because of
    // the nature of the mod menu in the first place
    public ActiveModsConfigPanel()
    {
        //super(ActiveModsMain.modID, ActiveModsMain.makeID("ActiveModsConfig"));
        super();


        //ModButton btnScan = new ModLabeledButton(/*stuff*/)

//        int x = 400;
//        int y = 700;
//
//        String text = String.format("(%d, %d)", x, y);
//        ModTextInput ti = new ModTextInput(text, x, y, 1000, 50, this, (me) -> {});
//
//        this.addUIElement(ti);

//        for (int x=0; x < 1000; x+=100)
//        {
//            for (int y=0; y<1000; y+=100)
//            {
//
//            }
//        }

        ModLabel comingSoon = new ModLabel("Coming soon!", LABEL_X, LABEL_Y, this, (me) ->
        {
            // leaving this for now even though it doesn't do anything
        });

        /*ModLabel comingSoonInfo = new ModLabel("For now, please manually edit the config file.", LABEL_X, LABEL_Y-100, this, (me) ->
        {
            // leaving this for now even though it doesn't do anything
        });

        ModLabel configPath = new ModLabel(ActiveModsMain.GetActiveMods().GetConfigPath(), LABEL_X, LABEL_Y-300, this, (me) -> {});

        ModLabeledButton showConfigPath = new ModLabeledButton("Show config path", LABEL_X, LABEL_Y-200, this, (me) ->
        {
            this.addUIElement(configPath);
        });*/

        this.addUIElement(comingSoon);
        /*this.addUIElement(comingSoonInfo);
        this.addUIElement(showConfigPath);*/
    }

}
