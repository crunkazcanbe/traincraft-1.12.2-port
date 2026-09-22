package train.client.render.renderSwitch.models;

import tmt.ModelConverter;
import tmt.ModelRendererTurbo;

public class ModelkSignal extends ModelConverter {
   int textureX = 128;
   int textureY = 128;

   public ModelkSignal() {
      this.bodyModel = new ModelRendererTurbo[67];
      this.initbodyModel_1();
      this.translateAll(0.0F, 0.0F, 0.0F);
      this.flipAll();
   }

   private void initbodyModel_1() {
      this.bodyModel[0] = new ModelRendererTurbo(this, 1, 1, this.textureX, this.textureY);
      this.bodyModel[1] = new ModelRendererTurbo(this, 33, 1, this.textureX, this.textureY);
      this.bodyModel[2] = new ModelRendererTurbo(this, 65, 1, this.textureX, this.textureY);
      this.bodyModel[3] = new ModelRendererTurbo(this, 89, 1, this.textureX, this.textureY);
      this.bodyModel[4] = new ModelRendererTurbo(this, 49, 9, this.textureX, this.textureY);
      this.bodyModel[5] = new ModelRendererTurbo(this, 1, 25, this.textureX, this.textureY);
      this.bodyModel[6] = new ModelRendererTurbo(this, 17, 41, this.textureX, this.textureY);
      this.bodyModel[7] = new ModelRendererTurbo(this, 57, 9, this.textureX, this.textureY);
      this.bodyModel[8] = new ModelRendererTurbo(this, 121, 17, this.textureX, this.textureY);
      this.bodyModel[9] = new ModelRendererTurbo(this, 1, 1, this.textureX, this.textureY);
      this.bodyModel[10] = new ModelRendererTurbo(this, 57, 1, this.textureX, this.textureY);
      this.bodyModel[11] = new ModelRendererTurbo(this, 89, 1, this.textureX, this.textureY);
      this.bodyModel[12] = new ModelRendererTurbo(this, 25, 25, this.textureX, this.textureY);
      this.bodyModel[13] = new ModelRendererTurbo(this, 113, 1, this.textureX, this.textureY);
      this.bodyModel[14] = new ModelRendererTurbo(this, 121, 1, this.textureX, this.textureY);
      this.bodyModel[15] = new ModelRendererTurbo(this, 33, 25, this.textureX, this.textureY);
      this.bodyModel[16] = new ModelRendererTurbo(this, 41, 25, this.textureX, this.textureY);
      this.bodyModel[17] = new ModelRendererTurbo(this, 89, 25, this.textureX, this.textureY);
      this.bodyModel[18] = new ModelRendererTurbo(this, 89, 33, this.textureX, this.textureY);
      this.bodyModel[19] = new ModelRendererTurbo(this, 105, 33, this.textureX, this.textureY);
      this.bodyModel[20] = new ModelRendererTurbo(this, 81, 41, this.textureX, this.textureY);
      this.bodyModel[21] = new ModelRendererTurbo(this, 1, 57, this.textureX, this.textureY);
      this.bodyModel[22] = new ModelRendererTurbo(this, 33, 57, this.textureX, this.textureY);
      this.bodyModel[23] = new ModelRendererTurbo(this, 97, 49, this.textureX, this.textureY);
      this.bodyModel[24] = new ModelRendererTurbo(this, 73, 49, this.textureX, this.textureY);
      this.bodyModel[25] = new ModelRendererTurbo(this, 65, 57, this.textureX, this.textureY);
      this.bodyModel[26] = new ModelRendererTurbo(this, 81, 57, this.textureX, this.textureY);
      this.bodyModel[27] = new ModelRendererTurbo(this, 105, 25, this.textureX, this.textureY);
      this.bodyModel[28] = new ModelRendererTurbo(this, 41, 33, this.textureX, this.textureY);
      this.bodyModel[29] = new ModelRendererTurbo(this, 1, 65, this.textureX, this.textureY);
      this.bodyModel[30] = new ModelRendererTurbo(this, 25, 65, this.textureX, this.textureY);
      this.bodyModel[31] = new ModelRendererTurbo(this, 41, 65, this.textureX, this.textureY);
      this.bodyModel[32] = new ModelRendererTurbo(this, 89, 65, this.textureX, this.textureY);
      this.bodyModel[33] = new ModelRendererTurbo(this, 1, 73, this.textureX, this.textureY);
      this.bodyModel[34] = new ModelRendererTurbo(this, 57, 65, this.textureX, this.textureY);
      this.bodyModel[35] = new ModelRendererTurbo(this, 113, 65, this.textureX, this.textureY);
      this.bodyModel[36] = new ModelRendererTurbo(this, 89, 9, this.textureX, this.textureY);
      this.bodyModel[37] = new ModelRendererTurbo(this, 1, 25, this.textureX, this.textureY);
      this.bodyModel[38] = new ModelRendererTurbo(this, 17, 25, this.textureX, this.textureY);
      this.bodyModel[39] = new ModelRendererTurbo(this, 97, 41, this.textureX, this.textureY);
      this.bodyModel[40] = new ModelRendererTurbo(this, 121, 49, this.textureX, this.textureY);
      this.bodyModel[41] = new ModelRendererTurbo(this, 17, 73, this.textureX, this.textureY);
      this.bodyModel[42] = new ModelRendererTurbo(this, 89, 57, this.textureX, this.textureY);
      this.bodyModel[43] = new ModelRendererTurbo(this, 73, 65, this.textureX, this.textureY);
      this.bodyModel[44] = new ModelRendererTurbo(this, 33, 73, this.textureX, this.textureY);
      this.bodyModel[45] = new ModelRendererTurbo(this, 41, 73, this.textureX, this.textureY);
      this.bodyModel[46] = new ModelRendererTurbo(this, 65, 73, this.textureX, this.textureY);
      this.bodyModel[47] = new ModelRendererTurbo(this, 33, 1, this.textureX, this.textureY);
      this.bodyModel[48] = new ModelRendererTurbo(this, 73, 73, this.textureX, this.textureY);
      this.bodyModel[49] = new ModelRendererTurbo(this, 81, 73, this.textureX, this.textureY);
      this.bodyModel[50] = new ModelRendererTurbo(this, 89, 73, this.textureX, this.textureY);
      this.bodyModel[51] = new ModelRendererTurbo(this, 97, 73, this.textureX, this.textureY);
      this.bodyModel[52] = new ModelRendererTurbo(this, 105, 73, this.textureX, this.textureY);
      this.bodyModel[53] = new ModelRendererTurbo(this, 25, 57, this.textureX, this.textureY);
      this.bodyModel[54] = new ModelRendererTurbo(this, 25, 73, this.textureX, this.textureY);
      this.bodyModel[55] = new ModelRendererTurbo(this, 49, 73, this.textureX, this.textureY);
      this.bodyModel[56] = new ModelRendererTurbo(this, 113, 73, this.textureX, this.textureY);
      this.bodyModel[57] = new ModelRendererTurbo(this, 1, 81, this.textureX, this.textureY);
      this.bodyModel[58] = new ModelRendererTurbo(this, 17, 81, this.textureX, this.textureY);
      this.bodyModel[59] = new ModelRendererTurbo(this, 33, 81, this.textureX, this.textureY);
      this.bodyModel[60] = new ModelRendererTurbo(this, 35, 89, this.textureX, this.textureY);
      this.bodyModel[61] = new ModelRendererTurbo(this, 41, 81, this.textureX, this.textureY);
      this.bodyModel[62] = new ModelRendererTurbo(this, 57, 80, this.textureX, this.textureY);
      this.bodyModel[63] = new ModelRendererTurbo(this, 9, 81, this.textureX, this.textureY);
      this.bodyModel[64] = new ModelRendererTurbo(this, 73, 81, this.textureX, this.textureY);
      this.bodyModel[65] = new ModelRendererTurbo(this, 8, 89, this.textureX, this.textureY);
      this.bodyModel[66] = new ModelRendererTurbo(this, 1, 89, this.textureX, this.textureY);
      this.bodyModel[0].addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10, 0.0F);
      this.bodyModel[0].setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bodyModel[1].addBox(-4.0F, 0.0F, -3.0F, 8, 1, 6, 0.0F);
      this.bodyModel[1].setRotationPoint(0.0F, -1.0F, 0.0F);
      this.bodyModel[2].addBox(-3.0F, -39.0F, -2.0F, 6, 39, 4, 0.0F);
      this.bodyModel[2].setRotationPoint(0.0F, -1.0F, 0.0F);
      this.bodyModel[3].addBox(-2.0F, 0.0F, -9.0F, 4, 4, 12, 0.0F);
      this.bodyModel[3].setRotationPoint(0.0F, -41.0F, 0.0F);
      this.bodyModel[4].addBox(-3.0F, -16.0F, -1.0F, 1, 16, 2, 0.0F);
      this.bodyModel[4].setRotationPoint(0.0F, -41.0F, -7.0F);
      this.bodyModel[5].addBox(0.0F, -16.0F, -4.0F, 2, 16, 8, 0.0F);
      this.bodyModel[5].setRotationPoint(1.0F, -53.0F, -7.0F);
      this.bodyModel[6].addBox(-4.0F, 0.0F, -5.0F, 14, 1, 10, 0.0F);
      this.bodyModel[6].setRotationPoint(0.0F, -42.0F, 0.0F);
      this.bodyModel[7].addBox(-0.5F, -40.0F, -0.5F, 1, 40, 1, 0.0F);
      this.bodyModel[7].setRotationPoint(-4.0F, -1.0F, 0.0F);
      this.bodyModel[8].addBox(0.0F, -16.0F, -1.0F, 1, 16, 2, 0.0F);
      this.bodyModel[8].setRotationPoint(0.0F, -41.0F, -7.0F);
      this.bodyModel[9].addBox(-2.0F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
      this.bodyModel[9].setRotationPoint(0.0F, -41.0F, -7.0F);
      this.bodyModel[10].addBox(-2.0F, -10.0F, -0.5F, 2, 2, 1, 0.0F);
      this.bodyModel[10].setRotationPoint(0.0F, -41.0F, -7.0F);
      this.bodyModel[11].addBox(-2.0F, -16.0F, -0.5F, 2, 2, 1, 0.0F);
      this.bodyModel[11].setRotationPoint(0.0F, -41.0F, -7.0F);
      this.bodyModel[12].addBox(0.0F, -14.0F, 0.0F, 1, 14, 1, 0.0F);
      this.bodyModel[12].setRotationPoint(-4.0F, -42.0F, -5.0F);
      this.bodyModel[13].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[13].setRotationPoint(9.0F, -42.0F, -5.0F);
      this.bodyModel[14].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[14].setRotationPoint(9.0F, -42.0F, 4.0F);
      this.bodyModel[15].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[15].setRotationPoint(-4.0F, -42.0F, 4.0F);
      this.bodyModel[16].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[16].setRotationPoint(1.0F, -42.0F, -5.0F);
      this.bodyModel[17].addBox(0.0F, -10.0F, 0.0F, 7, 1, 1, 0.0F);
      this.bodyModel[17].setRotationPoint(2.0F, -42.0F, -5.0F);
      this.bodyModel[18].addBox(0.0F, -6.0F, 0.0F, 7, 1, 1, 0.0F);
      this.bodyModel[18].setRotationPoint(2.0F, -42.0F, -5.0F);
      this.bodyModel[19].addBox(0.0F, -10.0F, 0.0F, 1, 1, 8, 0.0F);
      this.bodyModel[19].setRotationPoint(9.0F, -42.0F, -4.0F);
      this.bodyModel[20].addBox(0.0F, -6.0F, 0.0F, 1, 1, 8, 0.0F);
      this.bodyModel[20].setRotationPoint(9.0F, -42.0F, -4.0F);
      this.bodyModel[21].addBox(0.0F, -10.0F, 0.0F, 12, 1, 1, 0.0F);
      this.bodyModel[21].setRotationPoint(-3.0F, -42.0F, 4.0F);
      this.bodyModel[22].addBox(0.0F, -6.0F, 0.0F, 12, 1, 1, 0.0F);
      this.bodyModel[22].setRotationPoint(-3.0F, -42.0F, 4.0F);
      this.bodyModel[23].addBox(-10.0F, 0.0F, -5.0F, 7, 1, 8, 0.0F);
      this.bodyModel[23].setRotationPoint(0.0F, -57.0F, -7.0F);
      this.bodyModel[24].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[24].setRotationPoint(-4.0F, -57.0F, -5.0F);
      this.bodyModel[25].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[25].setRotationPoint(-10.0F, -57.0F, -12.0F);
      this.bodyModel[26].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[26].setRotationPoint(-10.0F, -57.0F, -5.0F);
      this.bodyModel[27].addBox(0.0F, -10.0F, 0.0F, 1, 1, 6, 0.0F);
      this.bodyModel[27].setRotationPoint(-10.0F, -57.0F, -11.0F);
      this.bodyModel[28].addBox(0.0F, -6.0F, 0.0F, 1, 1, 6, 0.0F);
      this.bodyModel[28].setRotationPoint(-10.0F, -57.0F, -11.0F);
      this.bodyModel[29].addBox(0.0F, -10.0F, 0.0F, 9, 1, 1, 0.0F);
      this.bodyModel[29].setRotationPoint(-9.0F, -57.0F, -12.0F);
      this.bodyModel[30].addBox(0.0F, -10.0F, 0.0F, 5, 1, 1, 0.0F);
      this.bodyModel[30].setRotationPoint(-9.0F, -57.0F, -5.0F);
      this.bodyModel[31].addBox(0.0F, -6.0F, 0.0F, 5, 1, 1, 0.0F);
      this.bodyModel[31].setRotationPoint(-9.0F, -57.0F, -5.0F);
      this.bodyModel[32].addBox(0.0F, -6.0F, 0.0F, 9, 1, 1, 0.0F);
      this.bodyModel[32].setRotationPoint(-9.0F, -57.0F, -12.0F);
      this.bodyModel[33].addBox(-3.0F, 0.0F, -5.0F, 4, 1, 4, 0.0F);
      this.bodyModel[33].setRotationPoint(0.0F, -57.0F, -7.0F);
      this.bodyModel[34].addBox(0.0F, -10.0F, 0.0F, 1, 10, 1, 0.0F);
      this.bodyModel[34].setRotationPoint(0.0F, -57.0F, -12.0F);
      this.bodyModel[35].addBox(0.0F, -1.5F, -1.5F, 4, 0, 3, 0.0F);
      this.bodyModel[35].setRotationPoint(3.0F, -65.0F, -7.0F);
      this.bodyModel[36].addBox(0.0F, -1.5F, -1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[36].setRotationPoint(3.0F, -65.0F, -7.0F);
      this.bodyModel[37].addBox(0.0F, -1.5F, 1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[37].setRotationPoint(3.0F, -65.0F, -7.0F);
      this.bodyModel[38].addBox(0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
      this.bodyModel[38].setRotationPoint(3.0F, -65.0F, -7.0F);
      this.bodyModel[39].addBox(0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
      this.bodyModel[39].setRotationPoint(3.0F, -61.5F, -9.0F);
      this.bodyModel[40].addBox(0.0F, -1.5F, 1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[40].setRotationPoint(3.0F, -61.5F, -9.0F);
      this.bodyModel[41].addBox(0.0F, -1.5F, -1.5F, 4, 0, 3, 0.0F);
      this.bodyModel[41].setRotationPoint(3.0F, -61.5F, -9.0F);
      this.bodyModel[42].addBox(0.0F, -1.5F, -1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[42].setRotationPoint(3.0F, -61.5F, -9.0F);
      this.bodyModel[43].addBox(0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
      this.bodyModel[43].setRotationPoint(3.0F, -61.5F, -5.0F);
      this.bodyModel[44].addBox(0.0F, -1.5F, 1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[44].setRotationPoint(3.0F, -61.5F, -5.0F);
      this.bodyModel[45].addBox(0.0F, -1.5F, -1.5F, 4, 0, 3, 0.0F);
      this.bodyModel[45].setRotationPoint(3.0F, -61.5F, -5.0F);
      this.bodyModel[46].addBox(0.0F, -1.5F, -1.5F, 3, 3, 0, 0.0F);
      this.bodyModel[46].setRotationPoint(3.0F, -61.5F, -5.0F);
      this.bodyModel[47].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[47].setRotationPoint(3.0F, -58.5F, -7.0F);
      this.bodyModel[48].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[48].setRotationPoint(3.0F, -58.5F, -9.0F);
      this.bodyModel[49].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[49].setRotationPoint(3.0F, -58.5F, -5.0F);
      this.bodyModel[50].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[50].setRotationPoint(3.0F, -56.5F, -10.0F);
      this.bodyModel[51].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[51].setRotationPoint(3.0F, -56.5F, -8.0F);
      this.bodyModel[52].addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.bodyModel[52].setRotationPoint(3.0F, -67.5F, -9.0F);
      this.bodyModel[53].addBox(-0.5F, -1.0F, 0.5F, 1, 1, 4, 0.0F);
      this.bodyModel[53].setRotationPoint(-4.0F, -5.0F, 0.0F);
      this.bodyModel[54].addBox(-0.5F, -1.0F, -4.5F, 1, 1, 4, 0.0F);
      this.bodyModel[54].setRotationPoint(-4.0F, -10.0F, 0.0F);
      this.bodyModel[55].addBox(-0.5F, -1.0F, -4.5F, 1, 1, 4, 0.0F);
      this.bodyModel[55].setRotationPoint(-4.0F, -20.0F, 0.0F);
      this.bodyModel[56].addBox(-0.5F, -1.0F, 0.5F, 1, 1, 4, 0.0F);
      this.bodyModel[56].setRotationPoint(-4.0F, -15.0F, 0.0F);
      this.bodyModel[57].addBox(-0.5F, -1.0F, -4.5F, 1, 1, 4, 0.0F);
      this.bodyModel[57].setRotationPoint(-4.0F, -30.0F, 0.0F);
      this.bodyModel[58].addBox(-0.5F, -1.0F, 0.5F, 1, 1, 4, 0.0F);
      this.bodyModel[58].setRotationPoint(-4.0F, -25.0F, 0.0F);
      this.bodyModel[59].addBox(-0.5F, -1.0F, 0.5F, 1, 1, 4, 0.0F);
      this.bodyModel[59].setRotationPoint(-4.0F, -35.0F, 0.0F);
      this.bodyModel[60].addBox(3.0F, -33.0F, -2.0F, 1, 12, 4, 0.0F);
      this.bodyModel[60].setRotationPoint(0.0F, -1.0F, 0.0F);
      this.bodyModel[61]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            6.0F,
            1.0F,
            2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F
         );
      this.bodyModel[61].setRotationPoint(4.0F, -41.0F, 8.0F);
      this.bodyModel[62]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            2.0F,
            1.0F,
            6.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F
         );
      this.bodyModel[62].setRotationPoint(1.0F, -37.0F, 1.0F);
      this.bodyModel[63]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            2.0F,
            1.0F,
            2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F
         );
      this.bodyModel[63].setRotationPoint(-2.0F, -41.0F, 1.0F);
      this.bodyModel[64]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            5.0F,
            1.0F,
            2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -1.0F,
            0.0F
         );
      this.bodyModel[64].setRotationPoint(-6.0F, -56.0F, 1.0F);
      this.bodyModel[65]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            1.0F,
            10.0F,
            2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -2.0F,
            0.0F,
            0.0F,
            -2.0F
         );
      this.bodyModel[65].setRotationPoint(5.0F, -21.0F, 9.0F);
      this.bodyModel[66]
         .addShapeBox(
            -2.0F,
            0.0F,
            -9.0F,
            1.0F,
            10.0F,
            2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            -2.0F,
            0.0F,
            0.0F,
            -2.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F,
            0.0F
         );
      this.bodyModel[66].setRotationPoint(5.0F, -21.0F, 7.0F);
   }
}
