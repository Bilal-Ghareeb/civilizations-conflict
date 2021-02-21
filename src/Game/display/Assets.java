package Game.display;

import java.awt.image.BufferedImage;

public class Assets {

    public static int bg = 1;

    public static BufferedImage start, start_pushed, options, options_pushed, quit,
            quit_pushed, help, help_pushed, back, back_pushed, pause, pause_pushed, background, logo,
            sound_off, sound_on, multiplayer, multiplayer_pushed, singleplayer, singleplayer_pushed, profile, profile_pushed,
            nomads, nomads_pushed, knights, knights_pushed, credits, castle, desert_tent, shop, shop_pushed,inGameMenu,inGameMenu2,menuBuildings,
            emptystone, stone, wood, gold, knights_description, nomads_description, map1, map2, map1_pushed, map2_pushed, visa,background1,
            background2, background3, background4, background1_pushed, background2_pushed, background3_pushed, background4_pushed;

    public static BufferedImage grass;
    public static BufferedImage TileSprite;
    public static BufferedImage TreeSprite;
    public static BufferedImage BuildingSprite;

    public static BufferedImage Walkingsprite;
    public static BufferedImage[] Tiles = new BufferedImage[15];

    public static void init() {
        if (bg == 1) {
            background = Image.loadImage("/rss/background1.png");
        } else if (bg == 2) {
            background = Image.loadImage("/rss/background2.png");
        } else if (bg == 3) {
            background = Image.loadImage("/rss/background3.png");
        } else if (bg == 4) {
            background = Image.loadImage("/rss/background4.png");
        }
        background1 = Image.loadImage("/rss/buttons/background1.png");
        background2 = Image.loadImage("/rss/buttons/background2.png");
        background3 = Image.loadImage("/rss/buttons/background3.png");
        background4 = Image.loadImage("/rss/buttons/background4.png");
        background1_pushed = Image.loadImage("/rss/buttons/background1_pushed.png");
        background2_pushed = Image.loadImage("/rss/buttons/background2_pushed.png");
        background3_pushed = Image.loadImage("/rss/buttons/background3_pushed.png");
        background4_pushed = Image.loadImage("/rss/buttons/background4_pushed.png");
        credits = Image.loadImage("/rss/credits.png");
        castle = Image.loadImage("/rss/castle.png");
        desert_tent = Image.loadImage("/rss/desert_tent.png");
        logo = Image.loadImage("/rss/logo.png");
        start = Image.loadImage("/rss/buttons/start.png");
        start_pushed = Image.loadImage("/rss/buttons/start_pushed.png");
        options = Image.loadImage("/rss/buttons/options.png");
        options_pushed = Image.loadImage("/rss/buttons/options_pushed.png");
        quit = Image.loadImage("/rss/buttons/quit.png");
        quit_pushed = Image.loadImage("/rss/buttons/quit_pushed.png");
        help = Image.loadImage("/rss/buttons/help.png");
        help_pushed = Image.loadImage("/rss/buttons/help_pushed.png");
        back = Image.loadImage("/rss/buttons/back.png");
        back_pushed = Image.loadImage("/rss/buttons/back_pushed.png");
        pause = Image.loadImage("/rss/buttons/pause.png");
        pause_pushed = Image.loadImage("/rss/buttons/pause_pushed.png");
        sound_off = Image.loadImage("/rss/buttons/sound_off.png");
        sound_on = Image.loadImage("/rss/buttons/sound_on.png");
        multiplayer = Image.loadImage("/rss/buttons/multiplayer.png");
        multiplayer_pushed = Image.loadImage("/rss/buttons/multiplayer_pushed.png");
        singleplayer = Image.loadImage("/rss/buttons/singleplayer.png");
        singleplayer_pushed = Image.loadImage("/rss/buttons/singleplayer_pushed.png");
        nomads = Image.loadImage("/rss/buttons/nomads.png");
        nomads_pushed = Image.loadImage("/rss/buttons/nomads_pushed.png");
        knights = Image.loadImage("/rss/buttons/knights.png");
        knights_pushed = Image.loadImage("/rss/buttons/knights_pushed.png");
        profile = Image.loadImage("/rss/buttons/profile.png");
        profile_pushed = Image.loadImage("/rss/buttons/profile_pushed.png");
        grass = Image.loadImage("/rss/grass.png");
        TileSprite = Image.loadImage("/rss/TileSprite.png");
        TreeSprite = Image.loadImage("/rss/TreeSprite.png");
        BuildingSprite = Image.loadImage("/rss/BuildingSprite.png");
        shop = Image.loadImage("/rss/buttons/shop.png");
        shop_pushed = Image.loadImage("/rss/buttons/shop_pushed.png");
        emptystone = Image.loadImage("/rss/buttons/emptystone.png");
        stone = Image.loadImage("/rss/stone.png");
        wood = Image.loadImage("/rss/wood.png");
        gold = Image.loadImage("/rss/gold.png");
        knights_description = Image.loadImage("/rss/knights_description.png");
        nomads_description = Image.loadImage("/rss/nomads_description.png");
        map1 = Image.loadImage("/rss/buttons/map1.png");
        map1_pushed = Image.loadImage("/rss/buttons/map1_pushed.png");
        map2 = Image.loadImage("/rss/buttons/map2.png");
        map2_pushed = Image.loadImage("/rss/buttons/map2_pushed.png");
        visa = Image.loadImage("/rss/visa.png");
        inGameMenu = Image.loadImage("/rss/menu.png");
        inGameMenu2 = Image.loadImage("/rss/menu2.png");
        menuBuildings = Image.loadImage("/rss/menuBSprite.png");
        
        //Tiles
        Tiles[0] = TileSprite.getSubimage(64, 0, 64, 64);
        Tiles[1] = TileSprite.getSubimage(64 * 3, 0, 64, 64);
        Tiles[2] = TileSprite.getSubimage(64 * 2, 0, 64, 64);
        Tiles[3] = TileSprite.getSubimage(64 * 4, 0, 64, 64);
        
        //Trees
        Tiles[4] = TreeSprite.getSubimage(64, 0, 64, 64);
        Tiles[5] = TreeSprite.getSubimage(64 * 2, 0, 64, 64);
        Tiles[6] = TreeSprite.getSubimage(64 * 3, 0, 64, 64);
        Tiles[7] = TreeSprite.getSubimage(64 * 4, 0, 64, 64);
        
        //Buldings
        Tiles[8] = BuildingSprite.getSubimage(128, 0, 128, 128);
        Tiles[9] = BuildingSprite.getSubimage(128 * 2, 0, 128, 128);
        Tiles[10] = BuildingSprite.getSubimage(128 * 3, 0, 128, 128);
        Tiles[11] = BuildingSprite.getSubimage(128 * 4, 0, 128, 128);

    }

}
