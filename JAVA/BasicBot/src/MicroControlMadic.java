import java.util.HashSet;
import java.util.Set;

import bwapi.Position;
import bwapi.UnitType;

// 매딕을 컨트롤 한다.
public class MicroControlMadic extends Manager {
    private final Set<UnitType> medicUnitTypeSet = new HashSet<>();
    private StrategyManager strategyManager = null;

    public MicroControlMadic() {
	medicUnitTypeSet.add(UnitType.Terran_Medic);
    }

    @Override
    protected void onStart(GameStatus gameStatus) {
	super.onStart(gameStatus);

	strategyManager = gameStatus.getStrategyManager();
    }

    @Override
    protected void onFrame() {
	super.onFrame();

	// 메딕은 42프레임(1초)에 1번만 컨트롤 한다.
	if (gameStatus.isMatchedInterval(1)) {
	    // Attack을 하면 매딕이 선두 바이오닉 유닛보다 훨씬 더 앞으로 가는 현상이 종종 발생한다.
	    // 선두 바이오닛 유닛이 건물을 때리는 동안 매딕은 공격 목표 지점으로 attack move 하는 것이다.
	    // 이 현상을 보정하기 위해서, 매딕이 선두 유닛과 100 pixel 이상 떨어지지 않도록 한다.
	    followBionicUnit();
	}
    }

    private void followBionicUnit() {
	if (strategyManager.isSkipMicroControl()) {
	    return;
	}

	Position newPosition = null;
	Set<Unit2> bionicSet = null;
	if (true == strategyManager.hasAttackTilePosition()) {
	    Position attackPosition = strategyManager.getAttackTilePositon().toPosition();
	    bionicSet = allianceUnitInfo.getUnitSet(UnitKind.Bionic_Unit);
	    // 메딕을 제외한 - 공격 목표 지점에서 가장 가까운 선두 바이오닉 유닛을 구한다.
	    Unit2 headBionicUnit = allianceUnitInfo.getClosestUnit(bionicSet, attackPosition, medicUnitTypeSet);
	    if (null == headBionicUnit) {
		Log.warn("선두 바이오닉 유닛이 존재하지 않아서 메딕을 컨트롤 할 수 없습니다.");
		return;
	    } else if (headBionicUnit.getPosition().equals(attackPosition)) {
		// 선두 바이오닉 유닛이 이미 공격 지점에 위치하고 있다면 매딕도 그 지점으로 이동한다.
		newPosition = attackPosition;
	    } else if (null != headBionicUnit) {
		// 선두 바이오닉 유닛으로부터 공격 목표 지점 방향으로 +100 position 거리의 위치를 구한다.
		newPosition = UnitUtil.getPositionAsDistance(headBionicUnit.getPosition(), attackPosition, 100);
	    }

	    if (null == newPosition) {
		Log.warn("메딕이 이동할 수 있는 거리를 계산하지 못했습니다. 바이오닉 유닛 개수=%d, 바이오닉 선두 위치=%s, 바이오닉 공격 대상 위치=%s, 메딕 이동 위치: null", bionicSet.size(),
			null != headBionicUnit ? headBionicUnit.getTilePosition() : "null", attackPosition);
		// TODO 메딕 계속 이동할까? 아니면 제자리에 있을까? 일단은 제자리에 대기...
	    } else {
		Set<Unit2> medicSet = allianceUnitInfo.getUnitSet(UnitKind.Terran_Medic);
		for (Unit2 medic : medicSet) {
		    boolean updated = ActionUtil.attackPosition(allianceUnitInfo, medic, newPosition);
		    if (updated) {
			Log.debug("메딕(%s)을 %s 위치로 이동한다.", medic, newPosition);
		    }
		}
	    }
	}
    }
}