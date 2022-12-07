package controllers.gameStates;

import controllers.TileManager;
import UI.presenters.statePresenters.CrawlingStatePresenter;
import UI.presenters.statePresenters.StatePresenter;
import controllers.DungeonController;
import entities.character.Player;
import entities.dungeon.DungeonDoor;
import settings.Settings;
import useCases.KeyEventHandler;
import useCases.playerUseCases.PlayerCollisionHandler;
import useCases.playerUseCases.PlayerMover;
import UI.presenters.PlayerViewModel;
import settings.Initializer;
import entities.dungeon.DungeonDoor.Door;

public class CrawlingState implements State, RoomSwitcher {
    /**
     * This class represents the state of the game where the player is free to move/roam around the map
     * There is no combat in this state, only movement and room transitions between various dungeon rooms.
     *
     * TLDR: Movement state (Non-Combat)
     */
    Player player;
    PlayerMover playerMover;
    PlayerViewModel playerViewModel;
    DungeonController dungeonController;
    TileManager tileManager;
    StatePresenter presenter;
    PlayerCollisionHandler playerCollisionHandler;

    /**
     * Creates a MainPlayingState object. Initializes the player, dungeon, playerMover.
     */
    public CrawlingState(){
        super();
        // Call the initializer
        Initializer initializer = new Initializer();
        initializer.init();
        this.player = initializer.getPlayer();
        // @TODO uncomment below code when dungeonController is done
        this.dungeonController = new DungeonController();
        this.playerMover = new PlayerMover(player);
        initializePresenter();
    }
    public void loop() {
        playerMover.move();
        playerViewModel.updatePosition();
        playerCollisionHandler.handleTileCollisions(tileManager.getCollisionArray());
        playerCollisionHandler.handleDoorCollisions(tileManager.getDoors());
        // @TODO call to DungeonRoomController
    }
    /**
     * Updates PlayerMover so that the associated direction boolean will be true
     * @param code - keyCode corresponding to the key
     */
    public void keyPressEvents(int code) {
        KeyEventHandler.handleCrawingStateEvents(code, true, playerMover);
    }
    /**
     * Updates PlayerMover so that the associated direciton boolean will be false (since key released)
     * @param code - keyCode corresponding to the key
     */
    public void keyReleasedEvents(int code) {
        KeyEventHandler.handleCrawingStateEvents(code, false, playerMover);
    }
    @Override
    public StatePresenter getPresenter() {
        return presenter;
    }
    public Player getPlayer(){
        return player;
    }
    /**
     * Helper Method for initializing the PlayerViewModel and the CrawlingStatePresenter
     */
    private void initializePresenter(){
        PlayerViewModel viewModel = new PlayerViewModel(player, Settings.getPlayerSize());
        tileManager = new TileManager();
        CrawlingStatePresenter crawlPresenter = new CrawlingStatePresenter();
        crawlPresenter.setPlayerViewModel(viewModel);
        crawlPresenter.setTilePresenter(tileManager);
        this.playerViewModel = viewModel;
        this.presenter = crawlPresenter;
        // Pass this as argument uses Dependency inversion so does not violate CA
        this.playerCollisionHandler =
                new PlayerCollisionHandler(playerViewModel.getCollisionRect(), playerMover, this);
    }

    @Override
    public void changeRoom(Door doorType) {
        switch (doorType){
            case BOTTOM:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case TOP_LEFT:
                break;
            case TOP_MID:
                break;
            case TOP_RIGHT:
                break;
        }
    }
}
