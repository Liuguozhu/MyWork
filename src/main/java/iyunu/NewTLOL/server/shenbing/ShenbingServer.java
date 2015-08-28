package iyunu.NewTLOL.server.shenbing;

import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.shenbing.res.ShenbingResetRes;
import iyunu.NewTLOL.util.Util;

public class ShenbingServer {

	public static void reset(Equip equip) {

		for (int i = 0; i < 4; i++) {

			ShenbingResetRes shenbingResetRes = ShenbingJson.instance().randomProperty(equip.getSteps());

			while (!ShenbingServer.getAddProperty(equip, shenbingResetRes)) {

			}

			if (shenbingResetRes != null) {
				AddProperty addProperty = new AddProperty();
				addProperty.setType(shenbingResetRes.getAddType());
				int sum = Util.getRandom(10000);
				int value = 1;
				if (sum <= shenbingResetRes.getFirstZone()) {
					value = Util.getRandom(shenbingResetRes.getFirstZoneMin(), shenbingResetRes.getFirstZoneMax());
				} else if (sum <= shenbingResetRes.getFirstZone() + shenbingResetRes.getSecondeZone()) {
					value = Util.getRandom(shenbingResetRes.getSecondeZoneMin(), shenbingResetRes.getSecondeZoneMax());
				} else if (sum <= shenbingResetRes.getFirstZone() + shenbingResetRes.getSecondeZone() + shenbingResetRes.getThirdZone()) {
					value = Util.getRandom(shenbingResetRes.getThirdZoneMin(), shenbingResetRes.getThirdZoneMax());
				} else {
					value = Util.getRandom(shenbingResetRes.getFourthZoneMin(), shenbingResetRes.getFourthZoneMax());
				}

				addProperty.setValue(value);
				addProperty.setMaxValue(shenbingResetRes.getMax());
				equip.getAddPropertyList().add(addProperty);
			}
		}
	}

	public static boolean getAddProperty(Equip equip, ShenbingResetRes shenbingResetRes) {

		int num = 1;
		for (AddProperty addProperty : equip.getAddPropertyList()) {
			if (addProperty.getType().equals(shenbingResetRes.getAddType())) {
				num++;
			}
		}

		return Util.probable(ShenbingJson.instance().getProbability(num));
	}
}
