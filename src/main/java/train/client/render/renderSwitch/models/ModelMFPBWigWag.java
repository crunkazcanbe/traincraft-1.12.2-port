package train.client.render.renderSwitch.models;

import tmt.ModelConverter;
import tmt.ModelRendererTurbo;

/**
 * Ported from Traincraft-5 FMT/TiM export (train.client.render.models.blocks.ModelMFPBWigWag).
 * Faithful static geometry: both source groups (group0 + Signal) flattened into one
 * bodyModel array. RollingStockModel arm-swing animation intentionally omitted (static).
 */
public class ModelMFPBWigWag extends ModelConverter
{
	int textureX = 256;
	int textureY = 256;

	public ModelMFPBWigWag()
	{
		bodyModel = new ModelRendererTurbo[272];

		initbodyModel_1();
		initbodyModel_2();
		initbodyModel_3();
		initbodyModel_4();
		initbodyModel_5();
		initbodyModel_6();
		initbodyModel_7();

		translateAll(0F, 0F, 0F);

		flipAll();
	}

	private void initbodyModel_1()
	{
		ModelRendererTurbo m0 = new ModelRendererTurbo(this, 117, 0, textureX, textureY);
		m0.addShapeBox(0, 0, 0, 7, 1, 7, 0, 0.0625f, 0, 0.0625f, 0.0625f, 0, 0.0625f, 0.0625f, 0, 0.0625f, 0.0625f, 0, 0.0625f, 0.1875f, 0, 0.1875f, 0.1875f, 0, 0.1875f, 0.1875f, 0, 0.1875f, 0.1875f, 0, 0.1875f);
		m0.setRotationPoint(-3.5f, -1, -3.5f);
		bodyModel[0] = m0;

		ModelRendererTurbo m1 = new ModelRendererTurbo(this, 80, 33, textureX, textureY);
		m1.addCylinder(0, 0, 0, 1, 4, 8, 1, 1, 4);
		m1.setRotationPoint(0, -5.3125f, 0);
		bodyModel[1] = m1;

		ModelRendererTurbo m2 = new ModelRendererTurbo(this, 18, 9, textureX, textureY);
		m2.addShapeBox(0, 0, 0, 4, 1, 4, 0, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f, 0.125f, 0, 0.125f);
		m2.setRotationPoint(-2, -2.25f, -2);
		bodyModel[2] = m2;

		ModelRendererTurbo m3 = new ModelRendererTurbo(this, 75, 33, textureX, textureY);
		m3.addShapeBox(0, 0, 0, 1, 3, 1, 0, 0, -0.25f, -0.3125f, -0.75f, -0.25f, -0.3125f, -0.75f, 0, -0.4375f, 0, 0, -0.4375f, 0, 0, 0, -0.75f, 0, 0, -0.75f, 0, -0.4375f, 0, 0, -0.4375f);
		m3.setRotationPoint(-0.125f, -5.25f, -1.5f);
		bodyModel[3] = m3;

		ModelRendererTurbo m4 = new ModelRendererTurbo(this, 70, 33, textureX, textureY);
		m4.addShapeBox(0, 0, 0, 1, 3, 1, 0, -0.4375f, 0, 0, -0.3125f, -0.25f, 0, -0.3125f, -0.25f, -0.75f, -0.4375f, 0, -0.75f, -0.4375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.4375f, 0, -0.75f);
		m4.setRotationPoint(0.5f, -5.25f, -0.125f);
		bodyModel[4] = m4;

		ModelRendererTurbo m5 = new ModelRendererTurbo(this, 221, 0, textureX, textureY);
		m5.addShapeBox(0, 0, 0, 3, 1, 6, 0, 0, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0);
		m5.setRotationPoint(-1.5f, -21.75f, -3);
		bodyModel[5] = m5;

		ModelRendererTurbo m6 = new ModelRendererTurbo(this, 102, 12, textureX, textureY);
		m6.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, 0, 0, -0.25f, 0, 0, -0.25f, 0, -0.9375f, 0, 0, -0.9375f, 0, -0.625f, 0, -0.25f, -0.625f, 0, -0.25f, -0.625f, -0.9375f, 0, -0.625f, -0.9375f);
		m6.setRotationPoint(-1.375f, -29.375f, -8.0625f);
		bodyModel[6] = m6;

		ModelRendererTurbo m7 = new ModelRendererTurbo(this, 153, 17, textureX, textureY);
		m7.addShapeBox(0, 0, 0, 1, 1, 5, 0, 0, -0.0625f, -0.125f, -0.625f, 0, 0, 1.75f, -2.4625f, -0.5f, -2.375f, -2.5625f, -0.3125f, 0, -0.875f, -0.125f, -0.625f, -0.9375f, 0, 1.75f, 1.55f, -0.5f, -2.375f, 1.6375f, -0.3125f);
		m7.setRotationPoint(-1.375f, -24.0625f, -7.8125f);
		bodyModel[7] = m7;

		ModelRendererTurbo m8 = new ModelRendererTurbo(this, 65, 33, textureX, textureY);
		m8.addShapeBox(0, 0, 0, 1, 5, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, 0, -0.9375f, 0, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, 0, -0.9375f, 0, 0);
		m8.setRotationPoint(0.5625f, -43.125f, -0.5f);
		bodyModel[8] = m8;

		ModelRendererTurbo m9 = new ModelRendererTurbo(this, 165, 0, textureX, textureY);
		m9.addShapeBox(0, -1.5f, -7.5f, 1, 3, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, 0, -0.9375f, -0.125f, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, 0, -0.9375f, -0.125f, 0);
		m9.setRotationPoint(0.625f, -41.75f, 0);
		m9.rotateAngleX = 0.78539816F;
		bodyModel[9] = m9;

		ModelRendererTurbo m10 = new ModelRendererTurbo(this, 202, 0, textureX, textureY);
		m10.addShapeBox(0, 0, 0, 5, 4, 4, 0, 0, 0, -0.25f, -0.5f, 0, -0.25f, -0.5f, 0, -0.25f, 0, 0, -0.25f, 0, -0.25f, -0.25f, -0.5f, -0.25f, -0.25f, -0.5f, -0.25f, -0.25f, 0, -0.25f, -0.25f);
		m10.setRotationPoint(-2, -36, -2);
		bodyModel[10] = m10;

		ModelRendererTurbo m11 = new ModelRendererTurbo(this, 247, 12, textureX, textureY);
		m11.addShapeBox(0, 0, 0, 1, 1, 3, 0, 0, -0.25f, 0, -0.5f, -0.25f, 0, -0.5f, -0.25f, 0, 0, -0.25f, 0, 0, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0, -0.5f, -0.5f);
		m11.setRotationPoint(1.5f, -32.5f, -1.5f);
		bodyModel[11] = m11;

		ModelRendererTurbo m12 = new ModelRendererTurbo(this, 208, 9, textureX, textureY);
		m12.addCylinder(0, 0, 0, 2, 1, 10, 0.875f, 0.875f, 3);
		m12.setRotationPoint(2.5f, -34.25f, 0);
		bodyModel[12] = m12;

		ModelRendererTurbo m13 = new ModelRendererTurbo(this, 248, 32, textureX, textureY);
		m13.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m13.setRotationPoint(2.375f, -34.75f, -0.5f);
		bodyModel[13] = m13;

		ModelRendererTurbo m14 = new ModelRendererTurbo(this, 84, 0, textureX, textureY);
		m14.addShapeBox(0, 0, 0, 7, 1, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0625f, -0.5f, 0.0625f, 0.0625f, -0.5f, 0.0625f, 0.0625f, -0.5f, 0.0625f, 0.0625f, -0.5f, 0.0625f);
		m14.setRotationPoint(-3.5f, -1.5f, -3.5f);
		bodyModel[14] = m14;

		ModelRendererTurbo m15 = new ModelRendererTurbo(this, 51, 0, textureX, textureY);
		m15.addShapeBox(0, 0, 0, 7, 1, 7, 0, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, 0, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, 0);
		m15.setRotationPoint(-3.5f, -1.75f, -3.5f);
		bodyModel[15] = m15;

		ModelRendererTurbo m16 = new ModelRendererTurbo(this, 18, 0, textureX, textureY);
		m16.addShapeBox(0, 0, 0, 7, 1, 7, 0, -0.6875f, 0, -0.6875f, -0.6875f, 0, -0.6875f, -0.6875f, 0, -0.6875f, -0.6875f, 0, -0.6875f, -0.1875f, -0.75f, -0.1875f, -0.1875f, -0.75f, -0.1875f, -0.1875f, -0.75f, -0.1875f, -0.1875f, -0.75f, -0.1875f);
		m16.setRotationPoint(-3.5f, -2, -3.5f);
		bodyModel[16] = m16;

		ModelRendererTurbo m17 = new ModelRendererTurbo(this, 228, 32, textureX, textureY);
		m17.addShapeBox(0, 0, 0, 1, 3, 1, 0, 0, 0, -0.4375f, -0.75f, 0, -0.4375f, -0.75f, -0.25f, -0.3125f, 0, -0.25f, -0.3125f, 0, 0, -0.4375f, -0.75f, 0, -0.4375f, -0.75f, 0, 0, 0, 0, 0);
		m17.setRotationPoint(-0.125f, -5.25f, 0.5f);
		bodyModel[17] = m17;

		ModelRendererTurbo m18 = new ModelRendererTurbo(this, 219, 32, textureX, textureY);
		m18.addShapeBox(0, 0, 0, 1, 3, 1, 0, -0.3125f, -0.25f, 0, -0.4375f, 0, 0, -0.4375f, 0, -0.75f, -0.3125f, -0.25f, -0.75f, 0, 0, 0, -0.4375f, 0, 0, -0.4375f, 0, -0.75f, 0, 0, -0.75f);
		m18.setRotationPoint(-1.5f, -5.25f, -0.125f);
		bodyModel[18] = m18;

		ModelRendererTurbo m19 = new ModelRendererTurbo(this, 150, 32, textureX, textureY);
		m19.addCylinder(0, 0, 0, 1, 13, 8, 0.625f, 0.625f, 4);
		m19.setRotationPoint(0, -18.25f, 0);
		bodyModel[19] = m19;

		ModelRendererTurbo m20 = new ModelRendererTurbo(this, 145, 32, textureX, textureY);
		m20.addCylinder(0, 0, 0, 1, 3, 8, 1, 1, 4);
		m20.setRotationPoint(0, -21.25f, 0);
		bodyModel[20] = m20;

		ModelRendererTurbo m21 = new ModelRendererTurbo(this, 143, 17, textureX, textureY);
		m21.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, -2.75f, -1.5f, 0, -2.75f, -1.5f, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, 2.25f, -2, 0, 2.25f, -2);
		m21.setRotationPoint(-1.5f, -24.5f, -8);
		bodyModel[21] = m21;

		ModelRendererTurbo m22 = new ModelRendererTurbo(this, 126, 17, textureX, textureY);
		m22.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f, -0.5f, -3.1875f, -1.9375f, 0, -3.1875f, -1.9375f, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, 2.25f, -2, 0, 2.25f, -2);
		m22.setRotationPoint(-1.4375f, -24.5f, -8);
		bodyModel[22] = m22;

		ModelRendererTurbo m23 = new ModelRendererTurbo(this, 109, 17, textureX, textureY);
		m23.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, 2.25f, -2, 0, 2.25f, -2, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, -2.75f, -1.5f, 0, -2.75f, -1.5f);
		m23.setRotationPoint(-1.5f, -30, -8);
		bodyModel[23] = m23;

		ModelRendererTurbo m24 = new ModelRendererTurbo(this, 140, 32, textureX, textureY);
		m24.addShapeBox(0, 0, 0, 1, 6, 1, 0, 0, 0, 0, -0.9375f, 0, 0, -0.9375f, -0.5f, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, -1, -0.5f, 0, -1, -0.5f);
		m24.setRotationPoint(-1.5f, -29.5f, -8);
		bodyModel[24] = m24;

		ModelRendererTurbo m25 = new ModelRendererTurbo(this, 92, 17, textureX, textureY);
		m25.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, 2.25f, -2, 0, 2.25f, -2, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f, -0.5f, -3.1875f, -1.9375f, 0, -3.1875f, -1.9375f);
		m25.setRotationPoint(-1.4375f, -30, -8);
		bodyModel[25] = m25;

		ModelRendererTurbo m26 = new ModelRendererTurbo(this, 135, 32, textureX, textureY);
		m26.addShapeBox(0, 0, 0, 1, 6, 1, 0, 0, 0, 0, -0.5f, 0, 0, -0.5f, -0.0625f, -0.9375f, 0, -0.0625f, -0.9375f, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f);
		m26.setRotationPoint(-1.4375f, -29.5f, -8);
		bodyModel[26] = m26;

		ModelRendererTurbo m27 = new ModelRendererTurbo(this, 51, 17, textureX, textureY);
		m27.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, -2.75f, -1.5f, -0.9375f, -2.75f, -1.5f, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, 2.25f, -2, -0.9375f, 2.25f, -2, -0.9375f, -0.5f, 0, 0, -0.5f, 0);
		m27.setRotationPoint(-1.5f, -24.5f, 1);
		bodyModel[27] = m27;

		ModelRendererTurbo m28 = new ModelRendererTurbo(this, 34, 17, textureX, textureY);
		m28.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, -3.1875f, -1.9375f, -0.5f, -3.1875f, -1.9375f, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, 0, 2.25f, -2, -0.5f, 2.25f, -2, -0.5f, -0.5f, 0, 0, -0.5f, 0);
		m28.setRotationPoint(-1.4375f, -24.5f, 1);
		bodyModel[28] = m28;

		ModelRendererTurbo m29 = new ModelRendererTurbo(this, 17, 17, textureX, textureY);
		m29.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, 2.25f, -2, -0.9375f, 2.25f, -2, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -2.75f, -1.5f, -0.9375f, -2.75f, -1.5f, -0.9375f, 0, -0.5f, 0, 0, -0.5f);
		m29.setRotationPoint(-1.5f, -30, 1);
		bodyModel[29] = m29;

		ModelRendererTurbo m30 = new ModelRendererTurbo(this, 130, 32, textureX, textureY);
		m30.addShapeBox(0, 0, 0, 1, 6, 1, 0, 0, -0.5f, -0.5f, -0.9375f, -0.5f, -0.5f, -0.9375f, 0, 0, 0, 0, 0, 0, -1, -0.5f, -0.9375f, -1, -0.5f, -0.9375f, -0.5f, 0, 0, -0.5f, 0);
		m30.setRotationPoint(-1.5f, -29.5f, 7);
		bodyModel[30] = m30;

		ModelRendererTurbo m31 = new ModelRendererTurbo(this, 0, 17, textureX, textureY);
		m31.addShapeBox(0, 0, 0, 1, 1, 7, 0, 0, 2.25f, -2, -0.5f, 2.25f, -2, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, -3.1875f, -1.9375f, -0.5f, -3.1875f, -1.9375f, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f);
		m31.setRotationPoint(-1.4375f, -30, 1);
		bodyModel[31] = m31;

		ModelRendererTurbo m32 = new ModelRendererTurbo(this, 125, 32, textureX, textureY);
		m32.addShapeBox(0, 0, 0, 1, 6, 1, 0, 0, -0.0625f, -0.9375f, -0.5f, -0.0625f, -0.9375f, -0.5f, 0, 0, 0, 0, 0, 0, -0.5625f, -0.9375f, -0.5f, -0.5625f, -0.9375f, -0.5f, -0.5f, 0, 0, -0.5f, 0);
		m32.setRotationPoint(-1.4375f, -29.5f, 7);
		bodyModel[32] = m32;

		ModelRendererTurbo m33 = new ModelRendererTurbo(this, 234, 16, textureX, textureY);
		m33.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, -2.75f, -1.5f, -0.9375f, -2.75f, -1.5f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, 2.25f, -2, -0.9375f, 2.25f, -2);
		m33.setRotationPoint(0.5f, -24.5f, -8);
		bodyModel[33] = m33;

		ModelRendererTurbo m34 = new ModelRendererTurbo(this, 224, 14, textureX, textureY);
		m34.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, 0, -3.1875f, -1.9375f, -0.5f, -3.1875f, -1.9375f, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, 2.25f, -2, -0.5f, 2.25f, -2);
		m34.setRotationPoint(0.4375f, -24.5f, -8);
		bodyModel[34] = m34;

		ModelRendererTurbo m35 = new ModelRendererTurbo(this, 198, 10, textureX, textureY);
		m35.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, 2.25f, -2, -0.9375f, 2.25f, -2, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, -2.75f, -1.5f, -0.9375f, -2.75f, -1.5f);
		m35.setRotationPoint(0.5f, -30, -8);
		bodyModel[35] = m35;

		ModelRendererTurbo m36 = new ModelRendererTurbo(this, 120, 32, textureX, textureY);
		m36.addShapeBox(0, 0, 0, 1, 6, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, -0.5f, -0.5f, -0.9375f, -0.5f, -0.5f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -1, -0.5f, -0.9375f, -1, -0.5f);
		m36.setRotationPoint(0.5f, -29.5f, -8);
		bodyModel[36] = m36;

		ModelRendererTurbo m37 = new ModelRendererTurbo(this, 214, 8, textureX, textureY);
		m37.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, 2.25f, -2, -0.5f, 2.25f, -2, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, 0, -3.1875f, -1.9375f, -0.5f, -3.1875f, -1.9375f);
		m37.setRotationPoint(0.4375f, -30, -8);
		bodyModel[37] = m37;

		ModelRendererTurbo m38 = new ModelRendererTurbo(this, 115, 32, textureX, textureY);
		m38.addShapeBox(0, 0, 0, 1, 6, 1, 0, -0.5f, 0, 0, 0, 0, 0, 0, -0.0625f, -0.9375f, -0.5f, -0.0625f, -0.9375f, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5625f, -0.9375f, -0.5f, -0.5625f, -0.9375f);
		m38.setRotationPoint(0.4375f, -29.5f, -8);
		bodyModel[38] = m38;

		ModelRendererTurbo m39 = new ModelRendererTurbo(this, 183, 6, textureX, textureY);
		m39.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.9375f, -2.75f, -1.5f, 0, -2.75f, -1.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, 2.25f, -2, 0, 2.25f, -2, 0, -0.5f, 0, -0.9375f, -0.5f, 0);
		m39.setRotationPoint(0.5f, -24.5f, 1);
		bodyModel[39] = m39;
	}

	private void initbodyModel_2()
	{
		ModelRendererTurbo m40 = new ModelRendererTurbo(this, 150, 6, textureX, textureY);
		m40.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.5f, -3.1875f, -1.9375f, 0, -3.1875f, -1.9375f, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f, -0.5f, 2.25f, -2, 0, 2.25f, -2, 0, -0.5f, 0, -0.5f, -0.5f, 0);
		m40.setRotationPoint(0.4375f, -24.5f, 1);
		bodyModel[40] = m40;

		ModelRendererTurbo m41 = new ModelRendererTurbo(this, 233, 5, textureX, textureY);
		m41.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.9375f, 2.25f, -2, 0, 2.25f, -2, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, -2.75f, -1.5f, 0, -2.75f, -1.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f);
		m41.setRotationPoint(0.5f, -30, 1);
		bodyModel[41] = m41;

		ModelRendererTurbo m42 = new ModelRendererTurbo(this, 110, 32, textureX, textureY);
		m42.addShapeBox(0, 0, 0, 1, 6, 1, 0, -0.9375f, -0.5f, -0.5f, 0, -0.5f, -0.5f, 0, 0, 0, -0.9375f, 0, 0, -0.9375f, -1, -0.5f, 0, -1, -0.5f, 0, -0.5f, 0, -0.9375f, -0.5f, 0);
		m42.setRotationPoint(0.5f, -29.5f, 7);
		bodyModel[42] = m42;

		ModelRendererTurbo m43 = new ModelRendererTurbo(this, 162, 0, textureX, textureY);
		m43.addShapeBox(0, 0, 0, 1, 1, 7, 0, -0.5f, 2.25f, -2, 0, 2.25f, -2, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, -3.1875f, -1.9375f, 0, -3.1875f, -1.9375f, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f);
		m43.setRotationPoint(0.4375f, -30, 1);
		bodyModel[43] = m43;

		ModelRendererTurbo m44 = new ModelRendererTurbo(this, 105, 32, textureX, textureY);
		m44.addShapeBox(0, 0, 0, 1, 6, 1, 0, -0.5f, -0.0625f, -0.9375f, 0, -0.0625f, -0.9375f, 0, 0, 0, -0.5f, 0, 0, -0.5f, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f, 0, -0.5f, 0, -0.5f, -0.5f, 0);
		m44.setRotationPoint(0.4375f, -29.5f, 7);
		bodyModel[44] = m44;

		ModelRendererTurbo m45 = new ModelRendererTurbo(this, 100, 32, textureX, textureY);
		m45.addShapeBox(0, 0, 0, 1, 2, 1, 0, -0.5f, 0, 0, -0.25f, 0, -0.25f, -0.75f, 0, -0.1875f, 0, 0, -0.4375f, 0, 0, -0.5625f, -0.75f, 0, -0.8125f, -0.75f, 0, -0.1875f, 0, 0, -0.4375f);
		m45.setRotationPoint(0.5f, -21.25f, -1.3125f);
		bodyModel[45] = m45;

		ModelRendererTurbo m46 = new ModelRendererTurbo(this, 95, 32, textureX, textureY);
		m46.addShapeBox(0, 0, 0, 1, 2, 1, 0, 0, 0, -0.4375f, -0.75f, 0, -0.1875f, -0.25f, 0, -0.25f, -0.5f, 0, 0, 0, 0, -0.4375f, -0.75f, 0, -0.1875f, -0.75f, 0, -0.8125f, 0, 0, -0.5625f);
		m46.setRotationPoint(0.5f, -21.25f, 0.3125f);
		bodyModel[46] = m46;

		ModelRendererTurbo m47 = new ModelRendererTurbo(this, 90, 32, textureX, textureY);
		m47.addShapeBox(0, 0, 0, 1, 2, 1, 0, -0.25f, 0, -0.25f, -0.5f, 0, 0, 0, 0, -0.4375f, -0.75f, 0, -0.1875f, -0.75f, 0, -0.8125f, 0, 0, -0.5625f, 0, 0, -0.4375f, -0.75f, 0, -0.1875f);
		m47.setRotationPoint(-1.5f, -21.25f, -1.3125f);
		bodyModel[47] = m47;

		ModelRendererTurbo m48 = new ModelRendererTurbo(this, 60, 32, textureX, textureY);
		m48.addShapeBox(0, 0, 0, 1, 2, 1, 0, -0.75f, 0, -0.1875f, 0, 0, -0.4375f, -0.5f, 0, 0, -0.25f, 0, -0.25f, -0.75f, 0, -0.1875f, 0, 0, -0.4375f, 0, 0, -0.5625f, -0.75f, 0, -0.8125f);
		m48.setRotationPoint(-1.5f, -21.25f, 0.3125f);
		bodyModel[48] = m48;

		ModelRendererTurbo m49 = new ModelRendererTurbo(this, 55, 32, textureX, textureY);
		m49.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m49.setRotationPoint(-1.6875f, -29.6875f, -8.125f);
		bodyModel[49] = m49;

		ModelRendererTurbo m50 = new ModelRendererTurbo(this, 50, 32, textureX, textureY);
		m50.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m50.setRotationPoint(0.6875f, -29.6875f, -8.125f);
		bodyModel[50] = m50;

		ModelRendererTurbo m51 = new ModelRendererTurbo(this, 169, 9, textureX, textureY);
		m51.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, 0, 0, -0.25f, 0, 0, -0.25f, 0, -0.9375f, 0, 0, -0.9375f, 0, -0.625f, 0, -0.25f, -0.625f, 0, -0.25f, -0.625f, -0.9375f, 0, -0.625f, -0.9375f);
		m51.setRotationPoint(-1.375f, -24.5f, -8.0625f);
		bodyModel[51] = m51;

		ModelRendererTurbo m52 = new ModelRendererTurbo(this, 45, 32, textureX, textureY);
		m52.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m52.setRotationPoint(-1.6875f, -24.8125f, -8.125f);
		bodyModel[52] = m52;

		ModelRendererTurbo m53 = new ModelRendererTurbo(this, 40, 32, textureX, textureY);
		m53.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m53.setRotationPoint(0.6875f, -24.8125f, -8.125f);
		bodyModel[53] = m53;

		ModelRendererTurbo m54 = new ModelRendererTurbo(this, 160, 9, textureX, textureY);
		m54.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, 0, 0, -0.25f, 0, 0, -0.25f, 0, -0.9375f, 0, 0, -0.9375f, 0, -0.625f, 0, -0.25f, -0.625f, 0, -0.25f, -0.625f, -0.9375f, 0, -0.625f, -0.9375f);
		m54.setRotationPoint(-1.375f, -24.5f, 8);
		bodyModel[54] = m54;

		ModelRendererTurbo m55 = new ModelRendererTurbo(this, 30, 32, textureX, textureY);
		m55.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m55.setRotationPoint(-1.6875f, -24.8125f, 7.875f);
		bodyModel[55] = m55;

		ModelRendererTurbo m56 = new ModelRendererTurbo(this, 214, 31, textureX, textureY);
		m56.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m56.setRotationPoint(0.6875f, -24.8125f, 7.875f);
		bodyModel[56] = m56;

		ModelRendererTurbo m57 = new ModelRendererTurbo(this, 135, 9, textureX, textureY);
		m57.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, 0, 0, -0.25f, 0, 0, -0.25f, 0, -0.9375f, 0, 0, -0.9375f, 0, -0.625f, 0, -0.25f, -0.625f, 0, -0.25f, -0.625f, -0.9375f, 0, -0.625f, -0.9375f);
		m57.setRotationPoint(-1.375f, -29.375f, 8);
		bodyModel[57] = m57;

		ModelRendererTurbo m58 = new ModelRendererTurbo(this, 195, 31, textureX, textureY);
		m58.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m58.setRotationPoint(-1.6875f, -29.6875f, 7.875f);
		bodyModel[58] = m58;

		ModelRendererTurbo m59 = new ModelRendererTurbo(this, 190, 31, textureX, textureY);
		m59.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.75f, -0.4375f, -0.4375f, -0.75f);
		m59.setRotationPoint(0.6875f, -29.6875f, 7.875f);
		bodyModel[59] = m59;

		ModelRendererTurbo m60 = new ModelRendererTurbo(this, 185, 31, textureX, textureY);
		m60.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1);
		m60.setRotationPoint(-1.625f, -24.4375f, -7.625f);
		bodyModel[60] = m60;

		ModelRendererTurbo m61 = new ModelRendererTurbo(this, 180, 31, textureX, textureY);
		m61.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1);
		m61.setRotationPoint(0.625f, -22.15f, -3.4375f);
		bodyModel[61] = m61;

		ModelRendererTurbo m62 = new ModelRendererTurbo(this, 136, 17, textureX, textureY);
		m62.addShapeBox(0, 0, 0, 1, 1, 5, 0, -0.625f, 0, 0, 0, -0.0625f, -0.125f, -2.375f, -2.5625f, -0.3125f, 1.75f, -2.4625f, -0.5f, -0.625f, -0.9375f, 0, 0, -0.875f, -0.125f, -2.375f, 1.6375f, -0.3125f, 1.75f, 1.55f, -0.5f);
		m62.setRotationPoint(0.375f, -24.125f, -7.8125f);
		bodyModel[62] = m62;

		ModelRendererTurbo m63 = new ModelRendererTurbo(this, 175, 31, textureX, textureY);
		m63.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1);
		m63.setRotationPoint(0.625f, -24.5f, -7.625f);
		bodyModel[63] = m63;

		ModelRendererTurbo m64 = new ModelRendererTurbo(this, 170, 31, textureX, textureY);
		m64.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1);
		m64.setRotationPoint(-1.625f, -22.2125f, -3.4375f);
		bodyModel[64] = m64;

		ModelRendererTurbo m65 = new ModelRendererTurbo(this, 165, 31, textureX, textureY);
		m65.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.375f, -1, -0.4375f, -0.375f, -1);
		m65.setRotationPoint(-0.5f, -23.3375f, -5.5f);
		bodyModel[65] = m65;

		ModelRendererTurbo m66 = new ModelRendererTurbo(this, 119, 17, textureX, textureY);
		m66.addShapeBox(0, 0, 0, 1, 1, 5, 0, 0, -0.875f, -0.125f, -0.625f, -0.9375f, 0, 1.75f, 1.55f, -0.5f, -2.375f, 1.6375f, -0.3125f, 0, -0.0625f, -0.125f, -0.625f, 0, 0, 1.75f, -2.4625f, -0.5f, -2.375f, -2.5625f, -0.3125f);
		m66.setRotationPoint(-1.375f, -30.375f, -7.8125f);
		bodyModel[66] = m66;

		ModelRendererTurbo m67 = new ModelRendererTurbo(this, 85, 31, textureX, textureY);
		m67.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f);
		m67.setRotationPoint(0.625f, -30.0625f, -7.625f);
		bodyModel[67] = m67;

		ModelRendererTurbo m68 = new ModelRendererTurbo(this, 224, 30, textureX, textureY);
		m68.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f);
		m68.setRotationPoint(-1.625f, -32.3375f, -3.4375f);
		bodyModel[68] = m68;

		ModelRendererTurbo m69 = new ModelRendererTurbo(this, 102, 17, textureX, textureY);
		m69.addShapeBox(0, 0, 0, 1, 1, 5, 0, -0.625f, -0.9375f, 0, 0, -0.875f, -0.125f, -2.375f, 1.6375f, -0.3125f, 1.75f, 1.55f, -0.5f, -0.625f, 0, 0, 0, -0.0625f, -0.125f, -2.375f, -2.5625f, -0.3125f, 1.75f, -2.4625f, -0.5f);
		m69.setRotationPoint(0.375f, -30.4375f, -7.8125f);
		bodyModel[69] = m69;

		ModelRendererTurbo m70 = new ModelRendererTurbo(this, 160, 30, textureX, textureY);
		m70.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f);
		m70.setRotationPoint(-1.625f, -30, -7.625f);
		bodyModel[70] = m70;

		ModelRendererTurbo m71 = new ModelRendererTurbo(this, 155, 30, textureX, textureY);
		m71.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f);
		m71.setRotationPoint(0.625f, -32.275f, -3.4375f);
		bodyModel[71] = m71;

		ModelRendererTurbo m72 = new ModelRendererTurbo(this, 80, 30, textureX, textureY);
		m72.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.375f, -1, -0.4375f, -0.375f, -1, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f);
		m72.setRotationPoint(-0.5f, -31.175f, -5.5f);
		bodyModel[72] = m72;

		ModelRendererTurbo m73 = new ModelRendererTurbo(this, 44, 17, textureX, textureY);
		m73.addShapeBox(0, 0, 0, 1, 1, 5, 0, -2.375f, -2.5625f, -0.3125f, 1.75f, -2.4625f, -0.5f, -0.625f, 0, 0, 0, -0.0625f, -0.125f, -2.375f, 1.6375f, -0.3125f, 1.75f, 1.55f, -0.5f, -0.625f, -0.9375f, 0, 0, -0.875f, -0.125f);
		m73.setRotationPoint(-1.375f, -24.0625f, 2.8125f);
		bodyModel[73] = m73;

		ModelRendererTurbo m74 = new ModelRendererTurbo(this, 75, 30, textureX, textureY);
		m74.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f);
		m74.setRotationPoint(-1.625f, -24.4375f, 6.625f);
		bodyModel[74] = m74;

		ModelRendererTurbo m75 = new ModelRendererTurbo(this, 70, 30, textureX, textureY);
		m75.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f);
		m75.setRotationPoint(0.625f, -22.15f, 2.4375f);
		bodyModel[75] = m75;

		ModelRendererTurbo m76 = new ModelRendererTurbo(this, 27, 17, textureX, textureY);
		m76.addShapeBox(0, 0, 0, 1, 1, 5, 0, 1.75f, -2.4625f, -0.5f, -2.375f, -2.5625f, -0.3125f, 0, -0.0625f, -0.125f, -0.625f, 0, 0, 1.75f, 1.55f, -0.5f, -2.375f, 1.6375f, -0.3125f, 0, -0.875f, -0.125f, -0.625f, -0.9375f, 0);
		m76.setRotationPoint(0.375f, -24.125f, 2.8125f);
		bodyModel[76] = m76;

		ModelRendererTurbo m77 = new ModelRendererTurbo(this, 65, 30, textureX, textureY);
		m77.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f);
		m77.setRotationPoint(0.625f, -24.5f, 6.625f);
		bodyModel[77] = m77;

		ModelRendererTurbo m78 = new ModelRendererTurbo(this, 248, 29, textureX, textureY);
		m78.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f);
		m78.setRotationPoint(-1.625f, -22.2125f, 2.4375f);
		bodyModel[78] = m78;

		ModelRendererTurbo m79 = new ModelRendererTurbo(this, 219, 29, textureX, textureY);
		m79.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.375f, -1, -0.4375f, -0.375f, -1, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.4375f, 0.125f);
		m79.setRotationPoint(-0.5f, -23.3375f, 4.5f);
		bodyModel[79] = m79;
	}

	private void initbodyModel_3()
	{
		ModelRendererTurbo m80 = new ModelRendererTurbo(this, 10, 17, textureX, textureY);
		m80.addShapeBox(0, 0, 0, 1, 1, 5, 0, -2.375f, 1.6375f, -0.3125f, 1.75f, 1.55f, -0.5f, -0.625f, -0.9375f, 0, 0, -0.875f, -0.125f, -2.375f, -2.5625f, -0.3125f, 1.75f, -2.4625f, -0.5f, -0.625f, 0, 0, 0, -0.0625f, -0.125f);
		m80.setRotationPoint(-1.375f, -30.375f, 2.8125f);
		bodyModel[80] = m80;

		ModelRendererTurbo m81 = new ModelRendererTurbo(this, 150, 29, textureX, textureY);
		m81.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0);
		m81.setRotationPoint(0.625f, -30.0625f, 6.625f);
		bodyModel[81] = m81;

		ModelRendererTurbo m82 = new ModelRendererTurbo(this, 145, 29, textureX, textureY);
		m82.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.3125f, -1, -0.4375f, -0.3125f, -1, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.375f, 0.125f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0);
		m82.setRotationPoint(-1.625f, -32.3375f, 2.4375f);
		bodyModel[82] = m82;

		ModelRendererTurbo m83 = new ModelRendererTurbo(this, 0, 8, textureX, textureY);
		m83.addShapeBox(0, 0, 0, 1, 1, 5, 0, 1.75f, 1.55f, -0.5f, -2.375f, 1.6375f, -0.3125f, 0, -0.875f, -0.125f, -0.625f, -0.9375f, 0, 1.75f, -2.4625f, -0.5f, -2.375f, -2.5625f, -0.3125f, 0, -0.0625f, -0.125f, -0.625f, 0, 0);
		m83.setRotationPoint(0.375f, -30.4375f, 2.8125f);
		bodyModel[83] = m83;

		ModelRendererTurbo m84 = new ModelRendererTurbo(this, 140, 29, textureX, textureY);
		m84.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0);
		m84.setRotationPoint(-1.625f, -30, 6.625f);
		bodyModel[84] = m84;

		ModelRendererTurbo m85 = new ModelRendererTurbo(this, 135, 29, textureX, textureY);
		m85.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.25f, -1, -0.4375f, -0.25f, -1, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.3125f, 0.125f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0);
		m85.setRotationPoint(0.625f, -32.275f, 2.4375f);
		bodyModel[85] = m85;

		ModelRendererTurbo m86 = new ModelRendererTurbo(this, 130, 29, textureX, textureY);
		m86.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.4375f, -0.375f, -1, -0.4375f, -0.375f, -1, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.4375f, 0.125f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.5f, -0.875f, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0);
		m86.setRotationPoint(-0.5f, -31.2375f, 4.375f);
		bodyModel[86] = m86;

		ModelRendererTurbo m87 = new ModelRendererTurbo(this, 125, 29, textureX, textureY);
		m87.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f);
		m87.setRotationPoint(-0.5f, -3.5f, -1.5f);
		bodyModel[87] = m87;

		ModelRendererTurbo m88 = new ModelRendererTurbo(this, 120, 29, textureX, textureY);
		m88.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f);
		m88.setRotationPoint(-0.5f, -4.625f, -1.5f);
		bodyModel[88] = m88;

		ModelRendererTurbo m89 = new ModelRendererTurbo(this, 115, 29, textureX, textureY);
		m89.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f);
		m89.setRotationPoint(-0.5f, -3.5f, 0.5f);
		bodyModel[89] = m89;

		ModelRendererTurbo m90 = new ModelRendererTurbo(this, 110, 29, textureX, textureY);
		m90.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f, 0, -0.25f, -0.25f);
		m90.setRotationPoint(-0.5f, -4.625f, 0.5f);
		bodyModel[90] = m90;

		ModelRendererTurbo m91 = new ModelRendererTurbo(this, 105, 29, textureX, textureY);
		m91.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m91.setRotationPoint(-0.4375f, -3.5f, -1.5f);
		bodyModel[91] = m91;

		ModelRendererTurbo m92 = new ModelRendererTurbo(this, 100, 29, textureX, textureY);
		m92.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m92.setRotationPoint(-0.4375f, -4.625f, -1.5f);
		bodyModel[92] = m92;

		ModelRendererTurbo m93 = new ModelRendererTurbo(this, 95, 29, textureX, textureY);
		m93.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m93.setRotationPoint(-0.4375f, -3.5f, 0.5f);
		bodyModel[93] = m93;

		ModelRendererTurbo m94 = new ModelRendererTurbo(this, 90, 29, textureX, textureY);
		m94.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m94.setRotationPoint(-0.4375f, -4.625f, 0.5f);
		bodyModel[94] = m94;

		ModelRendererTurbo m95 = new ModelRendererTurbo(this, 60, 29, textureX, textureY);
		m95.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m95.setRotationPoint(-0.5625f, -3.5f, -1.5f);
		bodyModel[95] = m95;

		ModelRendererTurbo m96 = new ModelRendererTurbo(this, 55, 29, textureX, textureY);
		m96.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m96.setRotationPoint(-0.5625f, -4.625f, -1.5f);
		bodyModel[96] = m96;

		ModelRendererTurbo m97 = new ModelRendererTurbo(this, 50, 29, textureX, textureY);
		m97.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m97.setRotationPoint(-0.5625f, -3.5f, 0.5f);
		bodyModel[97] = m97;

		ModelRendererTurbo m98 = new ModelRendererTurbo(this, 45, 29, textureX, textureY);
		m98.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f, 0, -0.375f, -0.375f);
		m98.setRotationPoint(-0.5625f, -4.625f, 0.5f);
		bodyModel[98] = m98;

		ModelRendererTurbo m99 = new ModelRendererTurbo(this, 160, 19, textureX, textureY);
		m99.addShapeBox(0, 0, 0, 1, 1, 6, 0, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f);
		m99.setRotationPoint(-2.4375f, -32.75f, -3);
		bodyModel[99] = m99;

		ModelRendererTurbo m100 = new ModelRendererTurbo(this, 68, 19, textureX, textureY);
		m100.addShapeBox(0, 0, 0, 1, 1, 6, 0, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, -0.9375f, -0.5f, 0, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f);
		m100.setRotationPoint(0.5f, -32.75f, -3);
		bodyModel[100] = m100;

		ModelRendererTurbo m101 = new ModelRendererTurbo(this, 209, 17, textureX, textureY);
		m101.addShapeBox(0, 0, 0, 1, 1, 6, 0, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f);
		m101.setRotationPoint(-1.9375f, -32.75f, -3);
		bodyModel[101] = m101;

		ModelRendererTurbo m102 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		m102.addShapeBox(0, 0, 0, 1, 1, 6, 0, -0.5f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, 0, -0.4375f, -0.0625f, -0.5f, -0.4375f, -0.0625f);
		m102.setRotationPoint(0.4375f, -32.75f, -3);
		bodyModel[102] = m102;

		ModelRendererTurbo m103 = new ModelRendererTurbo(this, 126, 9, textureX, textureY);
		m103.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, 0, 0, 0, 0, 0, 0, 0, 0, -0.9375f, 0, 0, -0.9375f);
		m103.setRotationPoint(-1.5f, -33.25f, -3);
		bodyModel[103] = m103;

		ModelRendererTurbo m104 = new ModelRendererTurbo(this, 117, 9, textureX, textureY);
		m104.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, -0.9375f, 0, 0, -0.9375f, 0, 0, -0.9375f, -0.5625f, 0, -0.9375f, -0.5625f, 0, 0, 0, 0, 0, 0, 0, 0, -0.5625f, 0, 0, -0.5625f);
		m104.setRotationPoint(-1.5f, -33.25f, -2.9375f);
		bodyModel[104] = m104;

		ModelRendererTurbo m105 = new ModelRendererTurbo(this, 102, 9, textureX, textureY);
		m105.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, 0, 0, 0, 0, 0, 0, 0, 0, -0.9375f, 0, 0, -0.9375f);
		m105.setRotationPoint(-1.5f, -33.25f, 2.9375f);
		bodyModel[105] = m105;

		ModelRendererTurbo m106 = new ModelRendererTurbo(this, 217, 0, textureX, textureY);
		m106.addShapeBox(0, 0, 0, 3, 1, 1, 0, 0, -0.9375f, 0, 0, -0.9375f, 0, 0, -0.9375f, -0.5625f, 0, -0.9375f, -0.5625f, 0, 0, 0, 0, 0, 0, 0, 0, -0.5625f, 0, 0, -0.5625f);
		m106.setRotationPoint(-1.5f, -33.25f, 2.5f);
		bodyModel[106] = m106;

		ModelRendererTurbo m107 = new ModelRendererTurbo(this, 40, 29, textureX, textureY);
		m107.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.9375f, -1.25f, 0.5f, 0, -1.25f, 0.5f, 0, -0.6875f, -0.5f, -0.9375f, -0.6875f, -0.5f, -0.9375f, 0.6875f, 0.25f, 0, 0.6875f, 0.25f, 0, 0, 0, -0.9375f, 0, 0);
		m107.setRotationPoint(0.5f, -33.25f, -4);
		bodyModel[107] = m107;

		ModelRendererTurbo m108 = new ModelRendererTurbo(this, 35, 29, textureX, textureY);
		m108.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.9375f, 0, -0.5f, 0, 0, -0.5f, 0, -0.21f, 0, -0.9375f, -0.21f, 0, -0.9375f, -0.3125f, -0.5f, 0, -0.3125f, -0.5f, 0, 0, 0, -0.9375f, 0, 0);
		m108.setRotationPoint(0.5f, -36.25f, -4);
		bodyModel[108] = m108;

		ModelRendererTurbo m109 = new ModelRendererTurbo(this, 30, 29, textureX, textureY);
		m109.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.9375f, -0.6875f, -0.5f, 0, -0.6875f, -0.5f, 0, -1.25f, 0.5f, -0.9375f, -1.25f, 0.5f, -0.9375f, 0, 0, 0, 0, 0, 0, 0.6875f, 0.25f, -0.9375f, 0.6875f, 0.25f);
		m109.setRotationPoint(0.5f, -33.25f, 3);
		bodyModel[109] = m109;

		ModelRendererTurbo m110 = new ModelRendererTurbo(this, 25, 29, textureX, textureY);
		m110.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.9375f, -0.21f, 0, 0, -0.21f, 0, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, 0, 0, 0, 0, 0, 0, -0.3125f, -0.5f, -0.9375f, -0.3125f, -0.5f);
		m110.setRotationPoint(0.5f, -36.25f, 3);
		bodyModel[110] = m110;

		ModelRendererTurbo m111 = new ModelRendererTurbo(this, 20, 29, textureX, textureY);
		m111.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.9375f, 0, -1, 0, 0, -1, 0, -0.6875f, 0, -0.9375f, -0.6875f, 0, -0.9375f, -1, 2.5f, 0, -1, 2.5f, 0, -0.79f, -3, -0.9375f, -0.79f, -3);
		m111.setRotationPoint(0.5f, -39.25f, -1);
		bodyModel[111] = m111;

		ModelRendererTurbo m112 = new ModelRendererTurbo(this, 15, 29, textureX, textureY);
		m112.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.9375f, -0.6875f, 0, 0, -0.6875f, 0, 0, 0, -1, -0.9375f, 0, -1, -0.9375f, -0.79f, -3, 0, -0.79f, -3, 0, -1, 2.5f, -0.9375f, -1, 2.5f);
		m112.setRotationPoint(0.5f, -39.25f, 0);
		bodyModel[112] = m112;

		ModelRendererTurbo m113 = new ModelRendererTurbo(this, 10, 29, textureX, textureY);
		m113.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.5f, -0.185f, -0.9375f, 0, -0.185f, -0.9375f, 0, -0.21f, 0, -0.5f, -0.21f, 0, -0.5f, 0.03125f, -0.9375f, 0, 0.03125f, -0.9375f, 0, 0, 0, -0.5f, 0, 0);
		m113.setRotationPoint(0.4375f, -36.25f, -4);
		bodyModel[113] = m113;

		ModelRendererTurbo m114 = new ModelRendererTurbo(this, 5, 29, textureX, textureY);
		m114.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.5f, -0.21f, 0, 0, -0.21f, 0, 0, -0.185f, -0.9375f, -0.5f, -0.185f, -0.9375f, -0.5f, 0, 0, 0, 0, 0, 0, 0.03125f, -0.9375f, -0.5f, 0.03125f, -0.9375f);
		m114.setRotationPoint(0.4375f, -36.25f, 3);
		bodyModel[114] = m114;

		ModelRendererTurbo m115 = new ModelRendererTurbo(this, 0, 29, textureX, textureY);
		m115.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.5f, -0.625f, -1, 0, -0.625f, -1, 0, -0.6875f, 0, -0.5f, -0.6875f, 0, -0.5f, -0.815f, 2.0625f, 0, -0.815f, 2.0625f, 0, -0.79f, -3, -0.5f, -0.79f, -3);
		m115.setRotationPoint(0.4375f, -39.25f, -1);
		bodyModel[115] = m115;

		ModelRendererTurbo m116 = new ModelRendererTurbo(this, 243, 28, textureX, textureY);
		m116.addShapeBox(0, 0, 0, 1, 4, 1, 0, -0.5f, -0.6875f, 0, 0, -0.6875f, 0, 0, -0.625f, -1, -0.5f, -0.625f, -1, -0.5f, -0.79f, -3, 0, -0.79f, -3, 0, -0.815f, 2.0625f, -0.5f, -0.815f, 2.0625f);
		m116.setRotationPoint(0.4375f, -39.25f, 0);
		bodyModel[116] = m116;

		ModelRendererTurbo m117 = new ModelRendererTurbo(this, 238, 28, textureX, textureY);
		m117.addShapeBox(0, -1.5f, -7.5f, 1, 3, 1, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m117.setRotationPoint(0.6875f, -41.75f, 0);
		m117.rotateAngleX = 0.78539816F;
		bodyModel[117] = m117;

		ModelRendererTurbo m118 = new ModelRendererTurbo(this, 233, 28, textureX, textureY);
		m118.addShapeBox(0, -1.5f, 7.25f, 1, 3, 1, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m118.setRotationPoint(0.6875f, -41.75f, 0);
		m118.rotateAngleX = 0.78539816F;
		bodyModel[118] = m118;

		ModelRendererTurbo m119 = new ModelRendererTurbo(this, 132, 0, textureX, textureY);
		m119.addShapeBox(0, -1.5f, -7.25f, 1, 1, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.5f, -0.9375f, -0.125f, -0.5f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.5f, -0.9375f, -0.625f, -0.5f);
		m119.setRotationPoint(0.6875f, -41.75f, 0);
		m119.rotateAngleX = 0.78539816F;
		bodyModel[119] = m119;
	}

	private void initbodyModel_4()
	{
		ModelRendererTurbo m120 = new ModelRendererTurbo(this, 99, 0, textureX, textureY);
		m120.addShapeBox(0, 1, -7.25f, 1, 1, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.5f, -0.9375f, -0.125f, -0.5f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.5f, -0.9375f, -0.625f, -0.5f);
		m120.setRotationPoint(0.6875f, -41.75f, 0);
		m120.rotateAngleX = 0.78539816F;
		bodyModel[120] = m120;

		ModelRendererTurbo m121 = new ModelRendererTurbo(this, 214, 28, textureX, textureY);
		m121.addShapeBox(0, -1, -6.375f, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m121.setRotationPoint(0.6875f, -41.75f, 0);
		m121.rotateAngleX = 0.78539816F;
		bodyModel[121] = m121;

		ModelRendererTurbo m122 = new ModelRendererTurbo(this, 193, 28, textureX, textureY);
		m122.addShapeBox(0, -0.625f, -6.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m122.setRotationPoint(0.6875f, -41.75f, 0);
		m122.rotateAngleX = 0.78539816F;
		bodyModel[122] = m122;

		ModelRendererTurbo m123 = new ModelRendererTurbo(this, 188, 28, textureX, textureY);
		m123.addShapeBox(0, 0, -6.375f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m123.setRotationPoint(0.6875f, -41.75f, 0);
		m123.rotateAngleX = 0.78539816F;
		bodyModel[123] = m123;

		ModelRendererTurbo m124 = new ModelRendererTurbo(this, 183, 28, textureX, textureY);
		m124.addShapeBox(0, -0.875f, -6.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m124.setRotationPoint(0.6875f, -41.75f, 0);
		m124.rotateAngleX = 0.78539816F;
		bodyModel[124] = m124;

		ModelRendererTurbo m125 = new ModelRendererTurbo(this, 178, 28, textureX, textureY);
		m125.addShapeBox(0, 0.625f, -6.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m125.setRotationPoint(0.6875f, -41.75f, 0);
		m125.rotateAngleX = 0.78539816F;
		bodyModel[125] = m125;

		ModelRendererTurbo m126 = new ModelRendererTurbo(this, 173, 28, textureX, textureY);
		m126.addShapeBox(0, -1, -6.125f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m126.setRotationPoint(0.6875f, -41.75f, 0);
		m126.rotateAngleX = 0.78539816F;
		bodyModel[126] = m126;

		ModelRendererTurbo m127 = new ModelRendererTurbo(this, 85, 28, textureX, textureY);
		m127.addShapeBox(0, 0, -6.125f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m127.setRotationPoint(0.6875f, -41.75f, 0);
		m127.rotateAngleX = 0.78539816F;
		bodyModel[127] = m127;

		ModelRendererTurbo m128 = new ModelRendererTurbo(this, 228, 27, textureX, textureY);
		m128.addShapeBox(0, -0.625f, -5.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m128.setRotationPoint(0.6875f, -41.75f, 0);
		m128.rotateAngleX = 0.78539816F;
		bodyModel[128] = m128;

		ModelRendererTurbo m129 = new ModelRendererTurbo(this, 223, 27, textureX, textureY);
		m129.addShapeBox(0, 0.375f, -5.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m129.setRotationPoint(0.6875f, -41.75f, 0);
		m129.rotateAngleX = 0.78539816F;
		bodyModel[129] = m129;

		ModelRendererTurbo m130 = new ModelRendererTurbo(this, 168, 27, textureX, textureY);
		m130.addShapeBox(0, -0.875f, -4.625f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m130.setRotationPoint(0.6875f, -41.75f, 0);
		m130.rotateAngleX = 0.78539816F;
		bodyModel[130] = m130;

		ModelRendererTurbo m131 = new ModelRendererTurbo(this, 163, 27, textureX, textureY);
		m131.addShapeBox(0, -0.875f, -4.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m131.setRotationPoint(0.6875f, -41.75f, 0);
		m131.rotateAngleX = 0.78539816F;
		bodyModel[131] = m131;

		ModelRendererTurbo m132 = new ModelRendererTurbo(this, 158, 27, textureX, textureY);
		m132.addShapeBox(0, -1, -4.375f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m132.setRotationPoint(0.6875f, -41.75f, 0);
		m132.rotateAngleX = 0.78539816F;
		bodyModel[132] = m132;

		ModelRendererTurbo m133 = new ModelRendererTurbo(this, 80, 27, textureX, textureY);
		m133.addShapeBox(0, -0.125f, -4.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m133.setRotationPoint(0.6875f, -41.75f, 0);
		m133.rotateAngleX = 0.78539816F;
		bodyModel[133] = m133;

		ModelRendererTurbo m134 = new ModelRendererTurbo(this, 75, 27, textureX, textureY);
		m134.addShapeBox(0, -0.625f, -3.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m134.setRotationPoint(0.6875f, -41.75f, 0);
		m134.rotateAngleX = 0.78539816F;
		bodyModel[134] = m134;

		ModelRendererTurbo m135 = new ModelRendererTurbo(this, 70, 27, textureX, textureY);
		m135.addShapeBox(0, -0.75f, -4.375f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m135.setRotationPoint(0.6875f, -41.75f, 0);
		m135.rotateAngleX = 0.78539816F;
		bodyModel[135] = m135;

		ModelRendererTurbo m136 = new ModelRendererTurbo(this, 65, 27, textureX, textureY);
		m136.addShapeBox(0, 0, -4.375f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.5f, 0, -0.125f, -0.5f, 0, -0.125f, -0.25f, -0.9375f, -0.125f, -0.25f, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.125f, 0, -0.9375f, -0.125f, 0);
		m136.setRotationPoint(0.6875f, -41.75f, 0);
		m136.rotateAngleX = 0.78539816F;
		bodyModel[136] = m136;

		ModelRendererTurbo m137 = new ModelRendererTurbo(this, 249, 26, textureX, textureY);
		m137.addShapeBox(0, -1, -2.875f, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m137.setRotationPoint(0.6875f, -41.75f, 0);
		m137.rotateAngleX = 0.78539816F;
		bodyModel[137] = m137;

		ModelRendererTurbo m138 = new ModelRendererTurbo(this, 153, 26, textureX, textureY);
		m138.addShapeBox(0, -0.625f, -2.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m138.setRotationPoint(0.6875f, -41.75f, 0);
		m138.rotateAngleX = 0.78539816F;
		bodyModel[138] = m138;

		ModelRendererTurbo m139 = new ModelRendererTurbo(this, 148, 26, textureX, textureY);
		m139.addShapeBox(0, 0, -2.875f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m139.setRotationPoint(0.6875f, -41.75f, 0);
		m139.rotateAngleX = 0.78539816F;
		bodyModel[139] = m139;

		ModelRendererTurbo m140 = new ModelRendererTurbo(this, 143, 26, textureX, textureY);
		m140.addShapeBox(0, -0.875f, -2.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m140.setRotationPoint(0.6875f, -41.75f, 0);
		m140.rotateAngleX = 0.78539816F;
		bodyModel[140] = m140;

		ModelRendererTurbo m141 = new ModelRendererTurbo(this, 138, 26, textureX, textureY);
		m141.addShapeBox(0, 0.625f, -2.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m141.setRotationPoint(0.6875f, -41.75f, 0);
		m141.rotateAngleX = 0.78539816F;
		bodyModel[141] = m141;

		ModelRendererTurbo m142 = new ModelRendererTurbo(this, 133, 26, textureX, textureY);
		m142.addShapeBox(0, -1, -2.625f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m142.setRotationPoint(0.6875f, -41.75f, 0);
		m142.rotateAngleX = 0.78539816F;
		bodyModel[142] = m142;

		ModelRendererTurbo m143 = new ModelRendererTurbo(this, 128, 26, textureX, textureY);
		m143.addShapeBox(0, 0, -2.625f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m143.setRotationPoint(0.6875f, -41.75f, 0);
		m143.rotateAngleX = 0.78539816F;
		bodyModel[143] = m143;

		ModelRendererTurbo m144 = new ModelRendererTurbo(this, 123, 26, textureX, textureY);
		m144.addShapeBox(0, -0.625f, -1.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m144.setRotationPoint(0.6875f, -41.75f, 0);
		m144.rotateAngleX = 0.78539816F;
		bodyModel[144] = m144;

		ModelRendererTurbo m145 = new ModelRendererTurbo(this, 118, 26, textureX, textureY);
		m145.addShapeBox(0, -1, -1.125f, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m145.setRotationPoint(0.6875f, -41.75f, 0);
		m145.rotateAngleX = 0.78539816F;
		bodyModel[145] = m145;

		ModelRendererTurbo m146 = new ModelRendererTurbo(this, 113, 26, textureX, textureY);
		m146.addShapeBox(0, -0.625f, -1.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m146.setRotationPoint(0.6875f, -41.75f, 0);
		m146.rotateAngleX = 0.78539816F;
		bodyModel[146] = m146;

		ModelRendererTurbo m147 = new ModelRendererTurbo(this, 108, 26, textureX, textureY);
		m147.addShapeBox(0, 0, -1.125f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m147.setRotationPoint(0.6875f, -41.75f, 0);
		m147.rotateAngleX = 0.78539816F;
		bodyModel[147] = m147;

		ModelRendererTurbo m148 = new ModelRendererTurbo(this, 103, 26, textureX, textureY);
		m148.addShapeBox(0, -0.875f, -0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m148.setRotationPoint(0.6875f, -41.75f, 0);
		m148.rotateAngleX = 0.78539816F;
		bodyModel[148] = m148;

		ModelRendererTurbo m149 = new ModelRendererTurbo(this, 98, 26, textureX, textureY);
		m149.addShapeBox(0, 0.625f, -0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m149.setRotationPoint(0.6875f, -41.75f, 0);
		m149.rotateAngleX = 0.78539816F;
		bodyModel[149] = m149;

		ModelRendererTurbo m150 = new ModelRendererTurbo(this, 93, 26, textureX, textureY);
		m150.addShapeBox(0, -1, -0.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m150.setRotationPoint(0.6875f, -41.75f, 0);
		m150.rotateAngleX = 0.78539816F;
		bodyModel[150] = m150;

		ModelRendererTurbo m151 = new ModelRendererTurbo(this, 60, 26, textureX, textureY);
		m151.addShapeBox(0, 0, -0.875f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m151.setRotationPoint(0.6875f, -41.75f, 0);
		m151.rotateAngleX = 0.78539816F;
		bodyModel[151] = m151;

		ModelRendererTurbo m152 = new ModelRendererTurbo(this, 55, 26, textureX, textureY);
		m152.addShapeBox(0, -0.625f, -0.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m152.setRotationPoint(0.6875f, -41.75f, 0);
		m152.rotateAngleX = 0.78539816F;
		bodyModel[152] = m152;

		ModelRendererTurbo m153 = new ModelRendererTurbo(this, 50, 26, textureX, textureY);
		m153.addShapeBox(0, 0.375f, -1.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m153.setRotationPoint(0.6875f, -41.75f, 0);
		m153.rotateAngleX = 0.78539816F;
		bodyModel[153] = m153;

		ModelRendererTurbo m154 = new ModelRendererTurbo(this, 45, 26, textureX, textureY);
		m154.addShapeBox(0, -0.125f, -0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m154.setRotationPoint(0.6875f, -41.75f, 0);
		m154.rotateAngleX = 0.78539816F;
		bodyModel[154] = m154;

		ModelRendererTurbo m155 = new ModelRendererTurbo(this, 40, 26, textureX, textureY);
		m155.addShapeBox(0, 0.125f, -0.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m155.setRotationPoint(0.6875f, -41.75f, 0);
		m155.rotateAngleX = 0.78539816F;
		bodyModel[155] = m155;

		ModelRendererTurbo m156 = new ModelRendererTurbo(this, 35, 26, textureX, textureY);
		m156.addShapeBox(0, -0.25f, -0.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m156.setRotationPoint(0.6875f, -41.75f, 0);
		m156.rotateAngleX = 0.78539816F;
		bodyModel[156] = m156;

		ModelRendererTurbo m157 = new ModelRendererTurbo(this, 30, 26, textureX, textureY);
		m157.addShapeBox(0, -0.75f, -1.125f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m157.setRotationPoint(0.6875f, -41.75f, 0);
		m157.rotateAngleX = 0.78539816F;
		bodyModel[157] = m157;

		ModelRendererTurbo m158 = new ModelRendererTurbo(this, 25, 26, textureX, textureY);
		m158.addShapeBox(0, -1, 0.625f, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m158.setRotationPoint(0.6875f, -41.75f, 0);
		m158.rotateAngleX = 0.78539816F;
		bodyModel[158] = m158;

		ModelRendererTurbo m159 = new ModelRendererTurbo(this, 20, 26, textureX, textureY);
		m159.addShapeBox(0, -0.625f, 0.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m159.setRotationPoint(0.6875f, -41.75f, 0);
		m159.rotateAngleX = 0.78539816F;
		bodyModel[159] = m159;
	}

	private void initbodyModel_5()
	{
		ModelRendererTurbo m160 = new ModelRendererTurbo(this, 15, 26, textureX, textureY);
		m160.addShapeBox(0, 0, 0.625f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m160.setRotationPoint(0.6875f, -41.75f, 0);
		m160.rotateAngleX = 0.78539816F;
		bodyModel[160] = m160;

		ModelRendererTurbo m161 = new ModelRendererTurbo(this, 10, 26, textureX, textureY);
		m161.addShapeBox(0, -0.875f, 0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m161.setRotationPoint(0.6875f, -41.75f, 0);
		m161.rotateAngleX = 0.78539816F;
		bodyModel[161] = m161;

		ModelRendererTurbo m162 = new ModelRendererTurbo(this, 5, 26, textureX, textureY);
		m162.addShapeBox(0, 0.625f, 0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m162.setRotationPoint(0.6875f, -41.75f, 0);
		m162.rotateAngleX = 0.78539816F;
		bodyModel[162] = m162;

		ModelRendererTurbo m163 = new ModelRendererTurbo(this, 0, 26, textureX, textureY);
		m163.addShapeBox(0, -1, 0.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m163.setRotationPoint(0.6875f, -41.75f, 0);
		m163.rotateAngleX = 0.78539816F;
		bodyModel[163] = m163;

		ModelRendererTurbo m164 = new ModelRendererTurbo(this, 244, 25, textureX, textureY);
		m164.addShapeBox(0, 0, 0.875f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m164.setRotationPoint(0.6875f, -41.75f, 0);
		m164.rotateAngleX = 0.78539816F;
		bodyModel[164] = m164;

		ModelRendererTurbo m165 = new ModelRendererTurbo(this, 239, 25, textureX, textureY);
		m165.addShapeBox(0, -0.625f, 1.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m165.setRotationPoint(0.6875f, -41.75f, 0);
		m165.rotateAngleX = 0.78539816F;
		bodyModel[165] = m165;

		ModelRendererTurbo m166 = new ModelRendererTurbo(this, 234, 25, textureX, textureY);
		m166.addShapeBox(0, 0.375f, 0.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m166.setRotationPoint(0.6875f, -41.75f, 0);
		m166.rotateAngleX = 0.78539816F;
		bodyModel[166] = m166;

		ModelRendererTurbo m167 = new ModelRendererTurbo(this, 219, 25, textureX, textureY);
		m167.addShapeBox(0, -0.125f, 0.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m167.setRotationPoint(0.6875f, -41.75f, 0);
		m167.rotateAngleX = 0.78539816F;
		bodyModel[167] = m167;

		ModelRendererTurbo m168 = new ModelRendererTurbo(this, 214, 25, textureX, textureY);
		m168.addShapeBox(0, 0.125f, 1.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m168.setRotationPoint(0.6875f, -41.75f, 0);
		m168.rotateAngleX = 0.78539816F;
		bodyModel[168] = m168;

		ModelRendererTurbo m169 = new ModelRendererTurbo(this, 88, 25, textureX, textureY);
		m169.addShapeBox(0, -0.25f, 0.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m169.setRotationPoint(0.6875f, -41.75f, 0);
		m169.rotateAngleX = 0.78539816F;
		bodyModel[169] = m169;

		ModelRendererTurbo m170 = new ModelRendererTurbo(this, 251, 23, textureX, textureY);
		m170.addShapeBox(0, -0.75f, 0.625f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m170.setRotationPoint(0.6875f, -41.75f, 0);
		m170.rotateAngleX = 0.78539816F;
		bodyModel[170] = m170;

		ModelRendererTurbo m171 = new ModelRendererTurbo(this, 229, 23, textureX, textureY);
		m171.addShapeBox(0, -0.875f, 2.375f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m171.setRotationPoint(0.6875f, -41.75f, 0);
		m171.rotateAngleX = 0.78539816F;
		bodyModel[171] = m171;

		ModelRendererTurbo m172 = new ModelRendererTurbo(this, 224, 23, textureX, textureY);
		m172.addShapeBox(0, -0.875f, 3.125f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m172.setRotationPoint(0.6875f, -41.75f, 0);
		m172.rotateAngleX = 0.78539816F;
		bodyModel[172] = m172;

		ModelRendererTurbo m173 = new ModelRendererTurbo(this, 83, 23, textureX, textureY);
		m173.addShapeBox(0, -0.875f, 4.375f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m173.setRotationPoint(0.6875f, -41.75f, 0);
		m173.rotateAngleX = 0.78539816F;
		bodyModel[173] = m173;

		ModelRendererTurbo m174 = new ModelRendererTurbo(this, 201, 22, textureX, textureY);
		m174.addShapeBox(0, -0.875f, 3.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, -1.375f, 0, -0.9375f, -1.375f, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, 0.75f, 0, -0.9375f, 0.75f, 0);
		m174.setRotationPoint(0.6875f, -41.75f, 0);
		m174.rotateAngleX = 0.78539816F;
		bodyModel[174] = m174;

		ModelRendererTurbo m175 = new ModelRendererTurbo(this, 169, 22, textureX, textureY);
		m175.addShapeBox(0, -1, 5.125f, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m175.setRotationPoint(0.6875f, -41.75f, 0);
		m175.rotateAngleX = 0.78539816F;
		bodyModel[175] = m175;

		ModelRendererTurbo m176 = new ModelRendererTurbo(this, 77, 22, textureX, textureY);
		m176.addShapeBox(0, -0.625f, 5.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m176.setRotationPoint(0.6875f, -41.75f, 0);
		m176.rotateAngleX = 0.78539816F;
		bodyModel[176] = m176;

		ModelRendererTurbo m177 = new ModelRendererTurbo(this, 69, 21, textureX, textureY);
		m177.addShapeBox(0, 0, 5.125f, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m177.setRotationPoint(0.6875f, -41.75f, 0);
		m177.rotateAngleX = 0.78539816F;
		bodyModel[177] = m177;

		ModelRendererTurbo m178 = new ModelRendererTurbo(this, 61, 21, textureX, textureY);
		m178.addShapeBox(0, -0.875f, 5.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m178.setRotationPoint(0.6875f, -41.75f, 0);
		m178.rotateAngleX = 0.78539816F;
		bodyModel[178] = m178;

		ModelRendererTurbo m179 = new ModelRendererTurbo(this, 0, 21, textureX, textureY);
		m179.addShapeBox(0, 0.625f, 5.375f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m179.setRotationPoint(0.6875f, -41.75f, 0);
		m179.rotateAngleX = 0.78539816F;
		bodyModel[179] = m179;

		ModelRendererTurbo m180 = new ModelRendererTurbo(this, 249, 20, textureX, textureY);
		m180.addShapeBox(0, -1, 5.375f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m180.setRotationPoint(0.6875f, -41.75f, 0);
		m180.rotateAngleX = 0.78539816F;
		bodyModel[180] = m180;

		ModelRendererTurbo m181 = new ModelRendererTurbo(this, 244, 20, textureX, textureY);
		m181.addShapeBox(0, 0, 5.375f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m181.setRotationPoint(0.6875f, -41.75f, 0);
		m181.rotateAngleX = 0.78539816F;
		bodyModel[181] = m181;

		ModelRendererTurbo m182 = new ModelRendererTurbo(this, 206, 19, textureX, textureY);
		m182.addShapeBox(0, -0.625f, 6.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.75f, -0.9375f, -0.75f, -0.75f);
		m182.setRotationPoint(0.6875f, -41.75f, 0);
		m182.rotateAngleX = 0.78539816F;
		bodyModel[182] = m182;

		ModelRendererTurbo m183 = new ModelRendererTurbo(this, 201, 19, textureX, textureY);
		m183.addShapeBox(0, 0.125f, 6.125f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m183.setRotationPoint(0.6875f, -41.75f, 0);
		m183.rotateAngleX = 0.78539816F;
		bodyModel[183] = m183;

		ModelRendererTurbo m184 = new ModelRendererTurbo(this, 169, 19, textureX, textureY);
		m184.addShapeBox(0, 0.125f, 5.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.5f, -0.9375f, 0, -0.5f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.5f, -0.9375f, -0.75f, -0.5f);
		m184.setRotationPoint(0.6875f, -41.75f, 0);
		m184.rotateAngleX = 0.78539816F;
		bodyModel[184] = m184;

		ModelRendererTurbo m185 = new ModelRendererTurbo(this, 66, 0, textureX, textureY);
		m185.addShapeBox(0, -1.5f, -7.5f, 1, 3, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, 0, -0.9375f, -0.125f, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, 0, -0.9375f, -0.125f, 0);
		m185.setRotationPoint(0.4375f, -41.75f, 0);
		m185.rotateAngleX = -0.78539816F;
		bodyModel[185] = m185;

		ModelRendererTurbo m186 = new ModelRendererTurbo(this, 92, 19, textureX, textureY);
		m186.addShapeBox(0, -1.5f, -7.5f, 1, 3, 1, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m186.setRotationPoint(0.5f, -41.75f, 0);
		m186.rotateAngleX = -0.78539816F;
		bodyModel[186] = m186;

		ModelRendererTurbo m187 = new ModelRendererTurbo(this, 87, 19, textureX, textureY);
		m187.addShapeBox(0, -1.5f, 7.25f, 1, 3, 1, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m187.setRotationPoint(0.5f, -41.75f, 0);
		m187.rotateAngleX = -0.78539816F;
		bodyModel[187] = m187;

		ModelRendererTurbo m188 = new ModelRendererTurbo(this, 33, 0, textureX, textureY);
		m188.addShapeBox(0, -1.5f, -7.25f, 1, 1, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.5f, -0.9375f, -0.125f, -0.5f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.5f, -0.9375f, -0.625f, -0.5f);
		m188.setRotationPoint(0.5f, -41.75f, 0);
		m188.rotateAngleX = -0.78539816F;
		bodyModel[188] = m188;

		ModelRendererTurbo m189 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		m189.addShapeBox(0, 1, -7.25f, 1, 1, 15, 0, -0.9375f, -0.125f, 0, 0, -0.125f, 0, 0, -0.125f, -0.5f, -0.9375f, -0.125f, -0.5f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.5f, -0.9375f, -0.625f, -0.5f);
		m189.setRotationPoint(0.5f, -41.75f, 0);
		m189.rotateAngleX = -0.78539816F;
		bodyModel[189] = m189;

		ModelRendererTurbo m190 = new ModelRendererTurbo(this, 82, 19, textureX, textureY);
		m190.addShapeBox(0, -0.875f, -7, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m190.setRotationPoint(0.5f, -41.75f, 0);
		m190.rotateAngleX = -0.78539816F;
		bodyModel[190] = m190;

		ModelRendererTurbo m191 = new ModelRendererTurbo(this, 77, 19, textureX, textureY);
		m191.addShapeBox(0, -0.875f, -6.75f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m191.setRotationPoint(0.5f, -41.75f, 0);
		m191.rotateAngleX = -0.78539816F;
		bodyModel[191] = m191;

		ModelRendererTurbo m192 = new ModelRendererTurbo(this, 65, 19, textureX, textureY);
		m192.addShapeBox(0, -1, -6.75f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m192.setRotationPoint(0.5f, -41.75f, 0);
		m192.rotateAngleX = -0.78539816F;
		bodyModel[192] = m192;

		ModelRendererTurbo m193 = new ModelRendererTurbo(this, 234, 18, textureX, textureY);
		m193.addShapeBox(0, -0.125f, -6.75f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m193.setRotationPoint(0.5f, -41.75f, 0);
		m193.rotateAngleX = -0.78539816F;
		bodyModel[193] = m193;

		ModelRendererTurbo m194 = new ModelRendererTurbo(this, 161, 18, textureX, textureY);
		m194.addShapeBox(0, -0.625f, -6, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m194.setRotationPoint(0.5f, -41.75f, 0);
		m194.rotateAngleX = -0.78539816F;
		bodyModel[194] = m194;

		ModelRendererTurbo m195 = new ModelRendererTurbo(this, 249, 17, textureX, textureY);
		m195.addShapeBox(0, -0.75f, -6.75f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m195.setRotationPoint(0.5f, -41.75f, 0);
		m195.rotateAngleX = -0.78539816F;
		bodyModel[195] = m195;

		ModelRendererTurbo m196 = new ModelRendererTurbo(this, 244, 17, textureX, textureY);
		m196.addShapeBox(0, 0, -6.75f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.5f, 0, -0.125f, -0.5f, 0, -0.125f, -0.25f, -0.9375f, -0.125f, -0.25f, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.125f, 0, -0.9375f, -0.125f, 0);
		m196.setRotationPoint(0.5f, -41.75f, 0);
		m196.rotateAngleX = -0.78539816F;
		bodyModel[196] = m196;

		ModelRendererTurbo m197 = new ModelRendererTurbo(this, 223, 17, textureX, textureY);
		m197.addShapeBox(0, -0.875f, -5.25f, 1, 2, 1, 0, -0.9375f, -0.25f, -0.375f, 0, -0.25f, -0.375f, 0, -0.25f, -0.375f, -0.9375f, -0.25f, -0.375f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m197.setRotationPoint(0.5f, -41.75f, 0);
		m197.rotateAngleX = -0.78539816F;
		bodyModel[197] = m197;

		ModelRendererTurbo m198 = new ModelRendererTurbo(this, 218, 17, textureX, textureY);
		m198.addShapeBox(0, -0.875f, -4.25f, 1, 2, 1, 0, -0.9375f, -0.25f, 0.375f, 0, -0.25f, 0.375f, 0, -0.25f, -1.125f, -0.9375f, -0.25f, -1.125f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m198.setRotationPoint(0.5f, -41.75f, 0);
		m198.rotateAngleX = -0.78539816F;
		bodyModel[198] = m198;

		ModelRendererTurbo m199 = new ModelRendererTurbo(this, 153, 17, textureX, textureY);
		m199.addShapeBox(0, -0.875f, -4.875f, 1, 1, 1, 0, -0.9375f, 0, -0.0625f, 0, 0, -0.0625f, 0, 0, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.5f, -0.9375f, -0.75f, -0.5f);
		m199.setRotationPoint(0.5f, -41.75f, 0);
		m199.rotateAngleX = -0.78539816F;
		bodyModel[199] = m199;
	}

	private void initbodyModel_6()
	{
		ModelRendererTurbo m200 = new ModelRendererTurbo(this, 144, 17, textureX, textureY);
		m200.addShapeBox(0, 0.125f, -4.875f, 1, 1, 1, 0, -0.9375f, 0, -0.0625f, 0, 0, -0.0625f, 0, 0, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.5f, -0.9375f, -0.75f, -0.5f);
		m200.setRotationPoint(0.5f, -41.75f, 0);
		m200.rotateAngleX = -0.78539816F;
		bodyModel[200] = m200;

		ModelRendererTurbo m201 = new ModelRendererTurbo(this, 136, 17, textureX, textureY);
		m201.addShapeBox(0, -0.875f, -3.5f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m201.setRotationPoint(0.5f, -41.75f, 0);
		m201.rotateAngleX = -0.78539816F;
		bodyModel[201] = m201;

		ModelRendererTurbo m202 = new ModelRendererTurbo(this, 127, 17, textureX, textureY);
		m202.addShapeBox(0, -0.875f, -2.75f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m202.setRotationPoint(0.5f, -41.75f, 0);
		m202.rotateAngleX = -0.78539816F;
		bodyModel[202] = m202;

		ModelRendererTurbo m203 = new ModelRendererTurbo(this, 119, 17, textureX, textureY);
		m203.addShapeBox(0, 0.625f, -2.5f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m203.setRotationPoint(0.5f, -41.75f, 0);
		m203.rotateAngleX = -0.78539816F;
		bodyModel[203] = m203;

		ModelRendererTurbo m204 = new ModelRendererTurbo(this, 110, 17, textureX, textureY);
		m204.addShapeBox(0, -0.875f, 1.625f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m204.setRotationPoint(0.5f, -41.75f, 0);
		m204.rotateAngleX = -0.78539816F;
		bodyModel[204] = m204;

		ModelRendererTurbo m205 = new ModelRendererTurbo(this, 102, 17, textureX, textureY);
		m205.addShapeBox(0, -0.875f, 1.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m205.setRotationPoint(0.5f, -41.75f, 0);
		m205.rotateAngleX = -0.78539816F;
		bodyModel[205] = m205;

		ModelRendererTurbo m206 = new ModelRendererTurbo(this, 61, 17, textureX, textureY);
		m206.addShapeBox(0, -1, 1.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m206.setRotationPoint(0.5f, -41.75f, 0);
		m206.rotateAngleX = -0.78539816F;
		bodyModel[206] = m206;

		ModelRendererTurbo m207 = new ModelRendererTurbo(this, 52, 17, textureX, textureY);
		m207.addShapeBox(0, -0.125f, 1.875f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m207.setRotationPoint(0.5f, -41.75f, 0);
		m207.rotateAngleX = -0.78539816F;
		bodyModel[207] = m207;

		ModelRendererTurbo m208 = new ModelRendererTurbo(this, 44, 17, textureX, textureY);
		m208.addShapeBox(0, -0.625f, 2.625f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.5f, 0, 0, -0.5f, 0, 0, -0.5f, -0.75f, -0.9375f, -0.5f, -0.75f);
		m208.setRotationPoint(0.5f, -41.75f, 0);
		m208.rotateAngleX = -0.78539816F;
		bodyModel[208] = m208;

		ModelRendererTurbo m209 = new ModelRendererTurbo(this, 35, 17, textureX, textureY);
		m209.addShapeBox(0, -0.75f, 1.875f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m209.setRotationPoint(0.5f, -41.75f, 0);
		m209.rotateAngleX = -0.78539816F;
		bodyModel[209] = m209;

		ModelRendererTurbo m210 = new ModelRendererTurbo(this, 27, 17, textureX, textureY);
		m210.addShapeBox(0, 0, 1.875f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.5f, 0, -0.125f, -0.5f, 0, -0.125f, -0.25f, -0.9375f, -0.125f, -0.25f, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.125f, 0, -0.9375f, -0.125f, 0);
		m210.setRotationPoint(0.5f, -41.75f, 0);
		m210.rotateAngleX = -0.78539816F;
		bodyModel[210] = m210;

		ModelRendererTurbo m211 = new ModelRendererTurbo(this, 18, 17, textureX, textureY);
		m211.addShapeBox(0, -1, 3, 1, 1, 1, 0, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f);
		m211.setRotationPoint(0.5f, -41.75f, 0);
		m211.rotateAngleX = -0.78539816F;
		bodyModel[211] = m211;

		ModelRendererTurbo m212 = new ModelRendererTurbo(this, 10, 17, textureX, textureY);
		m212.addShapeBox(0, -0.625f, 3, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m212.setRotationPoint(0.5f, -41.75f, 0);
		m212.rotateAngleX = -0.78539816F;
		bodyModel[212] = m212;

		ModelRendererTurbo m213 = new ModelRendererTurbo(this, 172, 12, textureX, textureY);
		m213.addShapeBox(0, 0, 3, 1, 1, 1, 0, -0.9375f, -0.625f, 0, 0, -0.625f, 0, 0, -0.625f, -0.75f, -0.9375f, -0.625f, -0.75f, -0.9375f, -0.375f, 0, 0, -0.375f, 0, 0, -0.125f, -0.75f, -0.9375f, -0.125f, -0.75f);
		m213.setRotationPoint(0.5f, -41.75f, 0);
		m213.rotateAngleX = -0.78539816F;
		bodyModel[213] = m213;

		ModelRendererTurbo m214 = new ModelRendererTurbo(this, 167, 12, textureX, textureY);
		m214.addShapeBox(0, -0.875f, 3.25f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m214.setRotationPoint(0.5f, -41.75f, 0);
		m214.rotateAngleX = -0.78539816F;
		bodyModel[214] = m214;

		ModelRendererTurbo m215 = new ModelRendererTurbo(this, 142, 12, textureX, textureY);
		m215.addShapeBox(0, 0.625f, 3.25f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m215.setRotationPoint(0.5f, -41.75f, 0);
		m215.rotateAngleX = -0.78539816F;
		bodyModel[215] = m215;

		ModelRendererTurbo m216 = new ModelRendererTurbo(this, 137, 12, textureX, textureY);
		m216.addShapeBox(0, -1, 3.25f, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m216.setRotationPoint(0.5f, -41.75f, 0);
		m216.rotateAngleX = -0.78539816F;
		bodyModel[216] = m216;

		ModelRendererTurbo m217 = new ModelRendererTurbo(this, 132, 12, textureX, textureY);
		m217.addShapeBox(0, 0, 3.25f, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m217.setRotationPoint(0.5f, -41.75f, 0);
		m217.rotateAngleX = -0.78539816F;
		bodyModel[217] = m217;

		ModelRendererTurbo m218 = new ModelRendererTurbo(this, 127, 12, textureX, textureY);
		m218.addShapeBox(0, -0.625f, 4, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m218.setRotationPoint(0.5f, -41.75f, 0);
		m218.rotateAngleX = -0.78539816F;
		bodyModel[218] = m218;

		ModelRendererTurbo m219 = new ModelRendererTurbo(this, 0, 17, textureX, textureY);
		m219.addShapeBox(0, -0.875f, 4.375f, 1, 2, 1, 0, -0.9375f, -0.25f, -0.375f, 0, -0.25f, -0.375f, 0, -0.25f, -0.375f, -0.9375f, -0.25f, -0.375f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m219.setRotationPoint(0.5f, -41.75f, 0);
		m219.rotateAngleX = -0.78539816F;
		bodyModel[219] = m219;

		ModelRendererTurbo m220 = new ModelRendererTurbo(this, 234, 14, textureX, textureY);
		m220.addShapeBox(0, -0.875f, 5.375f, 1, 2, 1, 0, -0.9375f, -0.25f, 0.375f, 0, -0.25f, 0.375f, 0, -0.25f, -1.125f, -0.9375f, -0.25f, -1.125f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m220.setRotationPoint(0.5f, -41.75f, 0);
		m220.rotateAngleX = -0.78539816F;
		bodyModel[220] = m220;

		ModelRendererTurbo m221 = new ModelRendererTurbo(this, 122, 12, textureX, textureY);
		m221.addShapeBox(0, -0.875f, 4.75f, 1, 1, 1, 0, -0.9375f, 0, -0.0625f, 0, 0, -0.0625f, 0, 0, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.5f, -0.9375f, -0.75f, -0.5f);
		m221.setRotationPoint(0.5f, -41.75f, 0);
		m221.rotateAngleX = -0.78539816F;
		bodyModel[221] = m221;

		ModelRendererTurbo m222 = new ModelRendererTurbo(this, 117, 12, textureX, textureY);
		m222.addShapeBox(0, 0.125f, 4.75f, 1, 1, 1, 0, -0.9375f, 0, -0.0625f, 0, 0, -0.0625f, 0, 0, -0.5625f, -0.9375f, 0, -0.5625f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.5f, -0.9375f, -0.75f, -0.5f);
		m222.setRotationPoint(0.5f, -41.75f, 0);
		m222.rotateAngleX = -0.78539816F;
		bodyModel[222] = m222;

		ModelRendererTurbo m223 = new ModelRendererTurbo(this, 200, 12, textureX, textureY);
		m223.addShapeBox(0, -0.875f, 5.75f, 1, 2, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, -0.25f, 0, 0, -0.25f, 0, 0, -0.25f, -0.75f, -0.9375f, -0.25f, -0.75f);
		m223.setRotationPoint(0.5f, -41.75f, 0);
		m223.rotateAngleX = -0.78539816F;
		bodyModel[223] = m223;

		ModelRendererTurbo m224 = new ModelRendererTurbo(this, 150, 10, textureX, textureY);
		m224.addShapeBox(0, -0.875f, 6, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m224.setRotationPoint(0.5f, -41.75f, 0);
		m224.rotateAngleX = -0.78539816F;
		bodyModel[224] = m224;

		ModelRendererTurbo m225 = new ModelRendererTurbo(this, 183, 9, textureX, textureY);
		m225.addShapeBox(0, -1, 6, 1, 1, 1, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0);
		m225.setRotationPoint(0.5f, -41.75f, 0);
		m225.rotateAngleX = -0.78539816F;
		bodyModel[225] = m225;

		ModelRendererTurbo m226 = new ModelRendererTurbo(this, 233, 8, textureX, textureY);
		m226.addShapeBox(0, 0.625f, 6, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.25f, -0.9375f, 0, -0.25f, -0.9375f, -0.75f, 0, 0, -0.75f, 0, 0, -0.75f, -0.25f, -0.9375f, -0.75f, -0.25f);
		m226.setRotationPoint(0.5f, -41.75f, 0);
		m226.rotateAngleX = -0.78539816F;
		bodyModel[226] = m226;

		ModelRendererTurbo m227 = new ModelRendererTurbo(this, 8, 8, textureX, textureY);
		m227.addShapeBox(0, -0.625f, 6.75f, 1, 1, 1, 0, -0.9375f, 0, 0, 0, 0, 0, 0, 0, -0.75f, -0.9375f, 0, -0.75f, -0.9375f, 0.25f, 0, 0, 0.25f, 0, 0, 0.25f, -0.75f, -0.9375f, 0.25f, -0.75f);
		m227.setRotationPoint(0.5f, -41.75f, 0);
		m227.rotateAngleX = -0.78539816F;
		bodyModel[227] = m227;

		ModelRendererTurbo m228 = new ModelRendererTurbo(this, 0, 8, textureX, textureY);
		m228.addShapeBox(0, 0, 6, 1, 1, 1, 0, -0.9375f, -0.625f, -0.75f, 0, -0.625f, -0.75f, 0, -0.625f, 0, -0.9375f, -0.625f, 0, -0.9375f, -0.125f, -0.75f, 0, -0.125f, -0.75f, 0, -0.375f, 0, -0.9375f, -0.375f, 0);
		m228.setRotationPoint(0.5f, -41.75f, 0);
		m228.rotateAngleX = -0.78539816F;
		bodyModel[228] = m228;

		ModelRendererTurbo m229 = new ModelRendererTurbo(this, 183, 0, textureX, textureY);
		m229.addShapeBox(0, 0, 0, 5, 1, 4, 0, 0, 0, -0.5f, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, 0, 0, -0.5f, 0, -0.75f, -0.25f, -0.5f, -0.75f, -0.25f, -0.5f, -0.75f, -0.25f, 0, -0.75f, -0.25f);
		m229.setRotationPoint(-2, -36.25f, -2);
		bodyModel[229] = m229;

		ModelRendererTurbo m230 = new ModelRendererTurbo(this, 150, 0, textureX, textureY);
		m230.addShapeBox(0, 0, 0, 5, 1, 4, 0, 0, 0, -1, -0.5f, 0, -1, -0.5f, 0, -1, 0, 0, -1, 0, -0.75f, -0.5f, -0.5f, -0.75f, -0.5f, -0.5f, -0.75f, -0.5f, 0, -0.75f, -0.5f);
		m230.setRotationPoint(-2, -36.5f, -2);
		bodyModel[230] = m230;

		ModelRendererTurbo m231 = new ModelRendererTurbo(this, 234, 0, textureX, textureY);
		m231.addShapeBox(0, 0, 0, 5, 1, 3, 0, 0, 0, -1.25f, -0.5f, 0, -1.25f, -0.5f, 0, -1.25f, 0, 0, -1.25f, 0, -0.875f, -0.5f, -0.5f, -0.875f, -0.5f, -0.5f, -0.875f, -0.5f, 0, -0.875f, -0.5f);
		m231.setRotationPoint(-2, -36.625f, -1.5f);
		bodyModel[231] = m231;

		ModelRendererTurbo m232 = new ModelRendererTurbo(this, 93, 9, textureX, textureY);
		m232.addCylinder(0, 0, 0, 2, 1, 10, 0.875f, 0.8125f, 3);
		m232.setRotationPoint(2.625f, -34.25f, 0);
		bodyModel[232] = m232;

		ModelRendererTurbo m233 = new ModelRendererTurbo(this, 84, 9, textureX, textureY);
		m233.addCylinder(0, 0, 0, 2, 1, 10, 0.8125f, 0.625f, 3);
		m233.setRotationPoint(2.875f, -34.25f, 0);
		bodyModel[233] = m233;

		ModelRendererTurbo m234 = new ModelRendererTurbo(this, 69, 9, textureX, textureY);
		m234.addCylinder(0, 0, 0, 2, 1, 10, 0.625f, 0.375f, 3);
		m234.setRotationPoint(3.125f, -34.25f, 0);
		bodyModel[234] = m234;

		ModelRendererTurbo m235 = new ModelRendererTurbo(this, 183, 6, textureX, textureY);
		m235.addShapeBox(0, 0, 0, 1, 1, 1, 0, -0.875f, -0.375f, -0.375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.875f, -0.375f, -0.375f, -0.875f, -0.375f, -0.375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, -0.875f, -0.375f, -0.375f);
		m235.setRotationPoint(2.5f, -34.75f, -0.5f);
		bodyModel[235] = m235;

		ModelRendererTurbo m236 = new ModelRendererTurbo(this, 177, 34, textureX, textureY);
		m236.addCylinder(0, 0, 0, 2, 1, 10, 0.9375f, 0f, 3);
		m236.setRotationPoint(2.5625f, -34.25f, 0);
		m236.rotateAngleX = -1.88495559F;
		bodyModel[236] = m236;

		ModelRendererTurbo m237 = new ModelRendererTurbo(this, 166, 34, textureX, textureY);
		m237.addCylinder(0, 0, 0, 2, 1, 10, 0.9375f, 0f, 3);
		m237.setRotationPoint(2.625f, -34.25f, 0);
		m237.rotateAngleX = -1.88495559F;
		bodyModel[237] = m237;

		ModelRendererTurbo m238 = new ModelRendererTurbo(this, 233, 33, textureX, textureY);
		m238.addCylinder(0, 0, 0, 2, 1, 10, 0.875f, 0f, 3);
		m238.setRotationPoint(2.875f, -34.25f, 0);
		m238.rotateAngleX = -1.88495559F;
		bodyModel[238] = m238;

		ModelRendererTurbo m239 = new ModelRendererTurbo(this, 200, 33, textureX, textureY);
		m239.addCylinder(0, 0, 0, 2, 1, 10, 0.6875f, 0f, 3);
		m239.setRotationPoint(3.125f, -34.25f, 0);
		m239.rotateAngleX = -1.88495559F;
		bodyModel[239] = m239;
	}

	private void initbodyModel_7()
	{
		ModelRendererTurbo m240 = new ModelRendererTurbo(this, 155, 33, textureX, textureY);
		m240.addCylinder(0, 0, 0, 2, 1, 10, 0.92f, 0f, 3);
		m240.setRotationPoint(2.5f, -34.25f, 0);
		m240.rotateAngleX = -1.88495559F;
		bodyModel[240] = m240;

		ModelRendererTurbo m241 = new ModelRendererTurbo(this, 172, 4, textureX, textureY);
		m241.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f);
		m241.setRotationPoint(1.5625f, -36.5f, -2);
		bodyModel[241] = m241;

		ModelRendererTurbo m242 = new ModelRendererTurbo(this, 139, 4, textureX, textureY);
		m242.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f);
		m242.setRotationPoint(1.5625f, -36.5f, 1);
		bodyModel[242] = m242;

		ModelRendererTurbo m243 = new ModelRendererTurbo(this, 117, 4, textureX, textureY);
		m243.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f);
		m243.setRotationPoint(-2.0625f, -36.5f, -2);
		bodyModel[243] = m243;

		ModelRendererTurbo m244 = new ModelRendererTurbo(this, 106, 4, textureX, textureY);
		m244.addShapeBox(0, 0, 0, 1, 1, 1, 0, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f, 0, -0.4375f, -0.4375f);
		m244.setRotationPoint(-2.0625f, -36.5f, 1);
		bodyModel[244] = m244;

		ModelRendererTurbo m245 = new ModelRendererTurbo(this, 221, 3, textureX, textureY);
		m245.addShapeBox(0, -0.5f, -0.5f, 1, 1, 1, 0, -0.1875f, -0.25f, -0.25f, -0.125f, 0, 0, -0.125f, 0, 0, -0.1875f, -0.25f, -0.25f, -0.1875f, -0.25f, -0.25f, -0.125f, 0, 0, -0.125f, 0, 0, -0.1875f, -0.25f, -0.25f);
		m245.setRotationPoint(-2.875f, -34, 0);
		bodyModel[245] = m245;

		ModelRendererTurbo m246 = new ModelRendererTurbo(this, 150, 6, textureX, textureY);
		m246.addCylinder(3.8125f, 8, 0, 1, 1, 8, 0.4375f, 0.6875f, 2);
		m246.setRotationPoint(-3.5f, -34, 0);
		bodyModel[246] = m246;

		ModelRendererTurbo m247 = new ModelRendererTurbo(this, 73, 3, textureX, textureY);
		m247.addShapeBox(0, -0.5f, -0.5f, 1, 1, 1, 0, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f, -0.1875f, 0, -0.1875f);
		m247.setRotationPoint(-3.5f, -34, 0);
		bodyModel[247] = m247;

		ModelRendererTurbo m248 = new ModelRendererTurbo(this, 201, 25, textureX, textureY);
		m248.addCylinder(2.5312f, 8, 0, 3, 1, 12, 1, 1, 2);
		m248.setRotationPoint(-3.5f, -34, 0);
		bodyModel[248] = m248;

		ModelRendererTurbo m249 = new ModelRendererTurbo(this, 251, 5, textureX, textureY);
		m249.addShapeBox(0, -0.8125f, -0.5f, 1, 3, 1, 0, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f);
		m249.setRotationPoint(-3.5f, -34, 0);
		bodyModel[249] = m249;

		ModelRendererTurbo m250 = new ModelRendererTurbo(this, 51, 3, textureX, textureY);
		m250.addShapeBox(1.3125f, 2.5f, -0.5f, 1, 1, 1, 0, 0, -0.3125f, -0.3125f, 0.375f, -0.3125f, -0.3125f, 0.375f, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0.375f, -0.3125f, -0.3125f, 0.375f, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f);
		m250.setRotationPoint(-3.5f, -34, 0);
		bodyModel[250] = m250;

		ModelRendererTurbo m251 = new ModelRendererTurbo(this, 40, 3, textureX, textureY);
		m251.addShapeBox(0, 2.1875f, -0.5f, 1, 1, 1, 0, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.6092f, -0.2812f, -0.3125f, -0.125f, -0.5625f, -0.3125f, -0.125f, -0.5627f, -0.3125f, -0.6095f, -0.2812f, -0.3125f);
		m251.setRotationPoint(-3.5f, -34, 0);
		bodyModel[251] = m251;

		ModelRendererTurbo m252 = new ModelRendererTurbo(this, 18, 3, textureX, textureY);
		m252.addShapeBox(0.3125f, 2.5f, -0.5f, 1, 1, 1, 0, -0.5625f, -0.125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, -0.5625f, -0.125f, -0.3125f, -0.2964f, -0.5933f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, -0.2969f, -0.5933f, -0.3125f);
		m252.setRotationPoint(-3.5f, -34, 0);
		bodyModel[252] = m252;

		ModelRendererTurbo m253 = new ModelRendererTurbo(this, 9, 3, textureX, textureY);
		m253.addShapeBox(3, 2.8125f, -0.5f, 1, 1, 1, 0, -0.125f, -0.5625f, -0.3125f, -0.6092f, -0.2812f, -0.3125f, -0.6095f, -0.2812f, -0.3125f, -0.125f, -0.5627f, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f);
		m253.setRotationPoint(-3.5f, -34, 0);
		bodyModel[253] = m253;

		ModelRendererTurbo m254 = new ModelRendererTurbo(this, 0, 3, textureX, textureY);
		m254.addShapeBox(2.6875f, 2.5f, -0.5f, 1, 1, 1, 0, 0, -0.3125f, -0.3125f, -0.2964f, -0.5933f, -0.3125f, -0.2969f, -0.5933f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, -0.5625f, -0.125f, -0.3125f, -0.5625f, -0.125f, -0.3125f, 0, -0.3125f, -0.3125f);
		m254.setRotationPoint(-3.5f, -34, 0);
		bodyModel[254] = m254;

		ModelRendererTurbo m255 = new ModelRendererTurbo(this, 243, 5, textureX, textureY);
		m255.addShapeBox(3, 3.8125f, -0.5f, 1, 3, 1, 0, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0, -0.3125f, -0.3125f, 0.3125f, -0.3125f, -0.3125f, 0.3125f, -0.3125f, -0.3125f, 0.3125f, -0.3125f, -0.3125f, 0.3125f, -0.3125f);
		m255.setRotationPoint(-3.5f, -34, 0);
		bodyModel[255] = m255;

		ModelRendererTurbo m256 = new ModelRendererTurbo(this, 188, 19, textureX, textureY);
		m256.addCylinder(2.5938f, 8, 0, 3, 1, 12, 1, 0.895833f, 2);
		m256.setRotationPoint(-3.5f, -34, 0);
		bodyModel[256] = m256;

		ModelRendererTurbo m257 = new ModelRendererTurbo(this, 251, 0, textureX, textureY);
		m257.addCylinder(2.5f, 8, 0, 1, 2, 8, 1, 1, 2);
		m257.setRotationPoint(-3.5f, -34, 0);
		bodyModel[257] = m257;

		ModelRendererTurbo m258 = new ModelRendererTurbo(this, 175, 19, textureX, textureY);
		m258.addCylinder(2.4688f, 8, 0, 3, 1, 12, 1, 0.895833f, 2);
		m258.setRotationPoint(-3.5f, -34, 0);
		bodyModel[258] = m258;

		ModelRendererTurbo m259 = new ModelRendererTurbo(this, 198, 0, textureX, textureY);
		m259.addCylinder(3.625f, 8, 0, 1, 1, 8, 0.875f, 0.9375f, 2);
		m259.setRotationPoint(-3.5f, -34, 0);
		bodyModel[259] = m259;

		ModelRendererTurbo m260 = new ModelRendererTurbo(this, 172, 0, textureX, textureY);
		m260.addCylinder(3.75f, 8, 0, 1, 1, 8, 0.6875f, 0.875f, 2);
		m260.setRotationPoint(-3.5f, -34, 0);
		bodyModel[260] = m260;

		ModelRendererTurbo m261 = new ModelRendererTurbo(this, 139, 0, textureX, textureY);
		m261.addCylinder(1.5f, 8, 0, 1, 1, 8, 0.9375f, 0.875f, 2);
		m261.setRotationPoint(-3.5f, -34, 0);
		bodyModel[261] = m261;

		ModelRendererTurbo m262 = new ModelRendererTurbo(this, 117, 0, textureX, textureY);
		m262.addCylinder(1.625f, 8, 0, 1, 1, 8, 0.875f, 0.6875f, 2);
		m262.setRotationPoint(-3.75f, -34, 0);
		bodyModel[262] = m262;

		ModelRendererTurbo m263 = new ModelRendererTurbo(this, 106, 0, textureX, textureY);
		m263.addCylinder(1.6875f, 8, 0, 1, 1, 8, 0.6875f, 0.4375f, 2);
		m263.setRotationPoint(-3.9375f, -34, 0);
		bodyModel[263] = m263;

		ModelRendererTurbo m264 = new ModelRendererTurbo(this, 84, 0, textureX, textureY);
		m264.addShapeBox(2.9688f, 5.3125f, -0.5f, 1, 5, 1, 0, -0.4531f, 0, 0, -0.3907f, 0, 0, -0.3907f, 0, 0, -0.4531f, 0, 0, -0.4531f, 0.375f, 0, -0.3907f, 0.375f, 0, -0.3907f, 0.375f, 0, -0.4531f, 0.375f, 0);
		m264.setRotationPoint(-3.5f, -34, 0);
		bodyModel[264] = m264;

		ModelRendererTurbo m265 = new ModelRendererTurbo(this, 243, 5, textureX, textureY);
		m265.addShapeBox(2.9688f, 7.5f, -2.3125f, 1, 1, 5, 0, -0.4531f, 0, 0.375f, -0.3907f, 0, 0.375f, -0.3907f, 0, 0, -0.4531f, 0, 0, -0.4531f, 0, 0.375f, -0.3907f, 0, 0.375f, -0.3907f, 0, 0, -0.4531f, 0, 0);
		m265.setRotationPoint(-3.5f, -34, 0);
		bodyModel[265] = m265;

		ModelRendererTurbo m266 = new ModelRendererTurbo(this, 73, 0, textureX, textureY);
		m266.addShapeBox(0, -0.5f, -0.25f, 1, 1, 1, 0, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.1875f);
		m266.setRotationPoint(-3.5f, -34, 0);
		bodyModel[266] = m266;

		ModelRendererTurbo m267 = new ModelRendererTurbo(this, 51, 0, textureX, textureY);
		m267.addShapeBox(0, -0.5f, -1.125f, 1, 1, 1, 0, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.5625f, -0.4375f, 0, -0.1875f, -0.4375f, 0, -0.1875f);
		m267.setRotationPoint(-3.5f, -34, 0);
		bodyModel[267] = m267;

		ModelRendererTurbo m268 = new ModelRendererTurbo(this, 40, 0, textureX, textureY);
		m268.addShapeBox(-0.0625f, -0.4375f, -0.3125f, 1, 1, 1, 0, -0.4375f, 0, -0.6875f, -0.3125f, 0, -0.6875f, -0.3125f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.1875f, -0.4375f, -0.875f, -0.1875f);
		m268.setRotationPoint(-3.5f, -34, 0);
		bodyModel[268] = m268;

		ModelRendererTurbo m269 = new ModelRendererTurbo(this, 18, 0, textureX, textureY);
		m269.addShapeBox(-0.0625f, 0.3125f, -0.3125f, 1, 1, 1, 0, -0.4375f, 0, -0.6875f, -0.3125f, 0, -0.6875f, -0.3125f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.1875f, -0.4375f, -0.875f, -0.1875f);
		m269.setRotationPoint(-3.5f, -34, 0);
		bodyModel[269] = m269;

		ModelRendererTurbo m270 = new ModelRendererTurbo(this, 9, 0, textureX, textureY);
		m270.addShapeBox(-0.0625f, -0.4375f, -1.1875f, 1, 1, 1, 0, -0.4375f, 0, -0.6875f, -0.3125f, 0, -0.6875f, -0.3125f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.1875f, -0.4375f, -0.875f, -0.1875f);
		m270.setRotationPoint(-3.5f, -34, 0);
		bodyModel[270] = m270;

		ModelRendererTurbo m271 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		m271.addShapeBox(-0.0625f, 0.3125f, -1.1875f, 1, 1, 1, 0, -0.4375f, 0, -0.6875f, -0.3125f, 0, -0.6875f, -0.3125f, 0, -0.1875f, -0.4375f, 0, -0.1875f, -0.4375f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.6875f, -0.3125f, -0.875f, -0.1875f, -0.4375f, -0.875f, -0.1875f);
		m271.setRotationPoint(-3.5f, -34, 0);
		bodyModel[271] = m271;
	}

}
