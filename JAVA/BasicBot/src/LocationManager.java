import java.util.List;

import bwapi.TilePosition;
import bwapi.Unit;

public abstract class LocationManager extends Manager implements IMapLocation {

    protected TilePosition allianceBaseLocation = null; // 아군 본진 위치
    protected TilePosition enemyBaseLocation = null; // 적군 본진 위치
    protected List<TilePosition> baseLocations = null; // 맵 전체의 스타팅 포인트 위치들.
    private List<TilePosition> searchSequence = null; // 정찰할 위치(순서)
    protected List<TilePosition> trainingBuildings = null; // 배럭, 팩토리, 스타포트와 같은 병력 훈련용 타일의 위치
    protected List<TilePosition> baseEntranceBunkers = null; // 본진 입구 벙커를 지을 위치
    protected List<TilePosition> size3by2Buildings = null; // 3*2 사이즈 건물을 짓기 위한 위치들 (서플라이 디팟, 아마데미 등)
    protected List<TilePosition> baseRefineries = null; // 본진 가스를 짓기 위한 위치
    protected List<TilePosition> baseEntranceTurrets = null; // 본진 입구에 위치한 터렛의 위치
    private TilePosition baseEntranceChokePoint = null; // 본진 입구 방어를 위한 위치
    private TilePosition firstExtensionChokePoint = null; // 앞마당 입구 방어를 위한 위치

    @Override
    protected void onFrame() {
	super.onFrame();

	if (1 == gameStatus.getFrameCount()) {
	    init(gameStatus.getAllianceUnitManager().getFirstCommandCenter());
	}
    }

    // CommandCenter를 기준으로 아군 본진이 위치를 계산한다.
    @Override
    public void init(Unit commandCenter) {
	allianceBaseLocation = commandCenter.getTilePosition();
	baseLocations = initBaseLocations();
	searchSequence = initSearchSequence();
	trainingBuildings = initTrainingBuildings();
	baseEntranceBunkers = initBaseEntranceBunker();
	size3by2Buildings = init3by2SizeBuildings();
	baseRefineries = initBaseRefinery();
	baseEntranceTurrets = initBaseEntranceTurret();
	baseEntranceChokePoint = initBaseEntranceChokePoint();
	firstExtensionChokePoint = initFirstExtensionChokePoint();
    }

    // index번째 스타팅 포인트 위치를 리턴한다.
    @Override
    public TilePosition getBaseLocations(int index) {
	return baseLocations.get(index);
    }

    // 아군 본진의 위치를 리턴한다.
    @Override
    public TilePosition getAllianceBaseLocation() {
	return allianceBaseLocation;
    }

    // 적군 본진의 위치에 대한 Getter
    @Override
    public TilePosition getEnemyBaseLocation() {
	return enemyBaseLocation;
    }

    // 적군 본진의 위치에 대한 Setter
    @Override
    public void setEnemyStartLocation(TilePosition enemyBaseLocation) {
	this.enemyBaseLocation = enemyBaseLocation;
    }

    // 정찰할 위치(순서)를 리턴한다.
    @Override
    public List<TilePosition> getSearchSequence() {
	return searchSequence;
    }

    // 배럭, 팩토리, 스타포트와 같은 병력 훈련용 타일의 위치를 리턴한다.
    @Override
    public List<TilePosition> getTrainingBuildings() {
	return trainingBuildings;
    }

    // 본진 입구 벙커를 지을 위치를 리턴한다.
    @Override
    public List<TilePosition> getBaseEntranceBunker() {
	return baseEntranceBunkers;
    }

    // 3*2 사이즈 건물을 짓기 위한 위치들을 리턴한다. (서플라이 디팟, 아마데미 등)
    @Override
    public List<TilePosition> get3by2SizeBuildings() {
	return size3by2Buildings;
    }

    // 본진 가스를 짓기 위한 위치를 리턴한다.
    @Override
    public List<TilePosition> getBaseRefinery() {
	return baseRefineries;
    }

    // 본진 입구에 위치한 터렛의 위치를 리턴한다.
    @Override
    public List<TilePosition> getBaseEntranceTurret() {
	return baseEntranceTurrets;
    }

    // 본진 입구 방어를 위한 위치를 리턴한다.
    @Override
    public TilePosition getBaseEntranceChokePoint() {
	return baseEntranceChokePoint;
    }

    // 앞마당 입구 방어를 위한 위치를 리턴한다.
    @Override
    public TilePosition getFirstExtensionChokePoint() {
	return firstExtensionChokePoint;
    }
}