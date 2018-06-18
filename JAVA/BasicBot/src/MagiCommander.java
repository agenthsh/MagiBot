
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;

/// 실제 봇프로그램의 본체가 되는 class<br>
/// 스타크래프트 경기 도중 발생하는 이벤트들이 적절하게 처리되도록 해당 Manager 객체에게 이벤트를 전달하는 관리자 Controller 역할을 합니다
public class MagiCommander extends GameCommander {
    private TrainingManager trainingManager = TrainingManager.Instance();

    /// 경기가 시작될 때 일회적으로 발생하는 이벤트를 처리합니다
    public void onStart() {
	super.onStart();
	Log.setLogLevel(Log.Level.DEBUG);
	Log.info("Game has started");
	trainingManager.init();
    }

    /// 경기가 종료될 때 일회적으로 발생하는 이벤트를 처리합니다
    public void onEnd(boolean isWinner) {
	super.onEnd(isWinner);
	Log.info("Game has finished");
    }

    /// 경기 진행 중 매 프레임마다 발생하는 이벤트를 처리합니다
    public void onFrame() {
	if (MyBotModule.Broodwar.isPaused() || MyBotModule.Broodwar.self() == null || MyBotModule.Broodwar.self().isDefeated() || MyBotModule.Broodwar.self().leftGame()
		|| MyBotModule.Broodwar.enemy() == null || MyBotModule.Broodwar.enemy().isDefeated() || MyBotModule.Broodwar.enemy().leftGame()) {
	    return;
	}

	// 아군 베이스 위치. 적군 베이스 위치. 각 유닛들의 상태정보 등을 Map 자료구조에 저장/업데이트
	InformationManager.Instance().update();

	// 각 유닛의 위치를 자체 MapGrid 자료구조에 저장
	MapGrid.Instance().update();

	Log.info("onFrame() started");

	if (true == trainingManager.isFinished()) {
	    System.exit(0);
	}
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Create 될 때 발생하는 이벤트를 처리합니다
    public void onUnitCreate(Unit unit) {
	super.onUnitCreate(unit);
    }

    ///  유닛(건물/지상유닛/공중유닛)이 Destroy 될 때 발생하는 이벤트를 처리합니다
    public void onUnitDestroy(Unit unit) {
	super.onUnitDestroy(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Morph 될 때 발생하는 이벤트를 처리합니다<br>
    /// Zerg 종족의 유닛은 건물 건설이나 지상유닛/공중유닛 생산에서 거의 대부분 Morph 형태로 진행됩니다
    public void onUnitMorph(Unit unit) {
	super.onUnitMorph(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)의 소속 플레이어가 바뀔 때 발생하는 이벤트를 처리합니다<br>
    /// Gas Geyser에 어떤 플레이어가 Refinery 건물을 건설했을 때, Refinery 건물이 파괴되었을 때, Protoss 종족 Dark Archon 의 Mind Control 에 의해 소속 플레이어가 바뀔 때 발생합니다
    public void onUnitRenegade(Unit unit) {
	super.onUnitRenegade(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)의 하던 일 (건물 건설, 업그레이드, 지상유닛 훈련 등)이 끝났을 때 발생하는 이벤트를 처리합니다
    public void onUnitComplete(Unit unit) {
	super.onUnitComplete(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Discover 될 때 발생하는 이벤트를 처리합니다<br>
    /// 아군 유닛이 Create 되었을 때 라든가, 적군 유닛이 Discover 되었을 때 발생합니다
    public void onUnitDiscover(Unit unit) {
	super.onUnitDiscover(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Evade 될 때 발생하는 이벤트를 처리합니다<br>
    /// 유닛이 Destroy 될 때 발생합니다
    public void onUnitEvade(Unit unit) {
	super.onUnitEvade(unit);
	trainingManager.onUnitEvade(unit);
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Show 될 때 발생하는 이벤트를 처리합니다<br>
    /// 아군 유닛이 Create 되었을 때 라든가, 적군 유닛이 Discover 되었을 때 발생합니다
    public void onUnitShow(Unit unit) {
	super.onUnitShow(unit);
	Log.info("onUnitShow(%d)", unit.getID());
    }

    /// 유닛(건물/지상유닛/공중유닛)이 Hide 될 때 발생하는 이벤트를 처리합니다<br>
    /// 보이던 유닛이 Hide 될 때 발생합니다
    public void onUnitHide(Unit unit) {
	super.onUnitHide(unit);
    }

    /// 핵미사일 발사가 감지되었을 때 발생하는 이벤트를 처리합니다
    public void onNukeDetect(Position target) {
	super.onNukeDetect(target);
    }

    /// 다른 플레이어가 대결을 나갔을 때 발생하는 이벤트를 처리합니다
    public void onPlayerLeft(Player player) {
	super.onPlayerLeft(player);
    }

    /// 게임을 저장할 때 발생하는 이벤트를 처리합니다
    public void onSaveGame(String gameName) {
	super.onSaveGame(gameName);
    }

    /// 텍스트를 입력 후 엔터를 하여 다른 플레이어들에게 텍스트를 전달하려 할 때 발생하는 이벤트를 처리합니다
    public void onSendText(String text) {
	super.onSendText(text);
    }

    /// 다른 플레이어로부터 텍스트를 전달받았을 때 발생하는 이벤트를 처리합니다
    public void onReceiveText(Player player, String text) {
	super.onReceiveText(player, text);
    }
}