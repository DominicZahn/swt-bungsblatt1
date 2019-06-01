package org.iMage.HDrize;

import java.util.Random;

import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The tests for {@link MatrixCalculator}.
 *
 * @author Dominik Fuchss
 *
 */
public class MatrixCalculatorTest extends TestBase {

  private MatrixCalculator calc;

  /**
   * Setup tests.
   */
  @Before
  public void setup() {
    this.calc = new MatrixCalculator();
  }

  /**
   * Just test multiplication of two matrices.
   */
  @Test
  public void testMultiplyStandard() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
    Matrix b = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 }, { 8, 9, 10 } });

    Matrix res = new Matrix(new double[][] { { 35.0, 41.0, 47.0 }, { 91.0, 109.0, 127.0 } });
    this.assertEquals(res, this.calc.multiply(a, b));
  }

  /**
   * Just test multiplication of two matrices with invalid dimensions.
   */
  @Test
  public void testMultiplyWrongDims() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
    Matrix b = new Matrix(new double[][] { { 1, 2, 3 }, { 1, 2, 3 } });
    // Check whether calculation is not possible
    Assert.assertNull(this.calc.multiply(a, b));
  }

  /**
   * Just test transpose of two matrices.
   */
  @Test
  public void testTranspose() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
    Matrix t = this.calc.transpose(a);

    Assert.assertEquals(a.cols(), t.rows());
    Assert.assertEquals(a.rows(), t.cols());

    for (int r = 0; r < a.rows(); r++) {
      for (int c = 0; c < a.cols(); c++) {
        Assert.assertEquals("Matrix differ at " + r + "," + c, //
            t.get(c, r), a.get(r, c), TestBase.EPS);
      }
    }
  }

  /**
   * Just test transpose of two matrices (creation of a copy).
   */
  @Test
  public void testTransposeCopy() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
    Matrix t = this.calc.transpose(a);
    Assert.assertNotSame(a, t);
    this.assertEquals(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } }, a);
  }

  /**
   * Just test inversion of a matrix (not possible due wrong dimensions).
   */
  @Test
  public void testInverseWrongDim() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 } });
    Assert.assertNull(this.calc.inverse(a));
  }

  /**
   * Just test inversion of a matrix (not possible due wrong rank).
   */
  @Test
  public void testInverseWrongRank() {
    Matrix a = new Matrix(new double[][] { { 1, 2, 3 }, { 5, 6, 7 }, { 10, 12, 14 } });
    Assert.assertNull(this.calc.inverse(a));
  }

  /**
   * Just test inversion of a matrix (small matrix).
   */
  @Test
  public void testSimpleInverse() {
    Matrix a = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 2, 0 }, { 0, 0, 3 } });
    Matrix inverse = new Matrix(new double[][] { { 1, 0, 0 }, { 0, .5, 0 }, { 0, 0, 1.0 / 3 } });
    this.assertEquals(inverse, this.calc.inverse(a));
  }

  /**
   * Just test inversion of a matrix (leading entry in snd line).
   */
  @Test
  public void testNotLeadingInverse() {
    Matrix a = new Matrix(new double[][] { { 0, 2, 0 }, { 1, 0, 0 }, { 0, 0, 3 } });
    Matrix inverse = new Matrix(new double[][] { { 0, 1, 0 }, { 0.5, 0, 0 }, { 0, 0, 1.0 / 3 } });
    this.assertEquals(inverse, this.calc.inverse(a));
  }

  /**
   * Just test inversion of a matrix (more than one set entry in line).
   */
  @Test
  public void testNotLeadingInverse2() {
    Matrix a = new Matrix(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } });
    Matrix inverse = new Matrix(new double[][] { //
        { -3.0 / 14, 1.0 / 7, 3.0 / 14 }, //
        { 1.0 / 14, 2.0 / 7, -1.0 / 14 }, //
        { 2.0 / 7, -4.0 / 21, 1.0 / 21 } //
    });
    this.assertEquals(inverse, this.calc.inverse(a));
  }

  /**
   * Just test inversion of a matrix (creation of a copy).
   */
  @Test
  public void testInverseCopy() {
    Matrix a = new Matrix(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } });
    Matrix i = this.calc.inverse(a);
    Assert.assertNotSame(a, i);
    this.assertEquals(new double[][] { { 0, 2, 3 }, { 1, 3, 0 }, { 4, 0, 3 } }, a);
  }

  /**
   * Just test inversion of a matrix (large 24x24 matrix).
   */
  @Test
  public void testBigInverse() {
    double[][] data = new double[24][24];
    Random random = new Random(42);
    for (int r = 0; r < 24; r++) {
      for (int c = 0; c < 24; c++) {
        data[r][c] = random.nextDouble();
      }
    }

    Matrix inverse = this.calc.inverse(new Matrix(data));

//@formatter:off
    double[][] expected = new double[][] {
      {-121.26921457674166, 27.298700098677518, 148.95772992126723, -10.995826933581172, -3.6907130281711718, 195.84604454654985, 37.28393320675882, -96.77655320039194, -79.24216134676583, -84.47870428124583, 199.54948892638362, -102.2176514778419, -52.301157353888, 133.47667090819255, 175.2086705921149, 85.94003569488405, -136.91308791544105, -22.07745008150514, -10.823047629623522, -201.20089129145154, -63.44821689072039, -24.186910084342024, 5.657639321708631, -63.9073353134282}            ,
      {38.7067735350437, -8.842431256497362, -47.513589408778486, 3.867437256718369, 0.8339259580499436, -61.960336250150434, -11.705775397644182, 29.797643773508888, 25.271300094028668, 26.59730923180052, -62.68848048928361, 32.060390396976494, 16.454447958628897, -41.44755254819617, -55.35445704659506, -26.660737435814628, 42.75824856100938, 7.652380512730578, 3.7107465647545963, 62.862354810542456, 20.605776361281915, 7.157651331295307, -1.7575979964206985, 20.081112925263536}              ,
      {88.78432216508516, -19.74911677101424, -109.34821563905359, 8.179422373591825, 3.256326209746334, -146.1308122983501, -27.1599902812305, 70.45882890527542, 57.71983786431832, 63.68792797614452, -146.64271419353054, 74.9337452143133, 38.89165458876754, -97.64860320109956, -128.96030684768644, -63.10299281512585, 101.0794872497291, 16.40103401693189, 8.570548635172292, 147.02119677414734, 47.02169155070253, 17.385825185515056, -4.1992289322822, 47.59643940772146}                          ,
      {150.14660406968994, -34.165963436983255, -184.07925398679643, 13.004142795811747, 4.4013158440494395, -243.11053593291777, -46.533023604928474, 119.9368512814156, 97.90564503018871, 105.94088639176813, -246.18034177646757, 126.69147283532911, 64.65253652124962, -165.28224671257615, -215.93441307771403, -105.97889251479624, 170.04197902430542, 28.183089044546826, 12.843496090234996, 247.95339700492772, 78.85126383049017, 29.019784904151354, -7.7799612512224074, 79.37652224569821}        ,
      {-27.132944624393172, 6.748979412579167, 32.4178446332767, -3.106409658583713, -0.9370602267716227, 42.226244290882086, 7.83146191514245, -20.84655658428272, -18.699032207056263, -18.58187315120368, 44.410845534302254, -21.924734558373363, -10.90992739726182, 28.404792746100917, 38.725658608050885, 19.223835883562103, -29.129707326978906, -6.1436016495157055, -2.066616903964308, -43.89507329744522, -14.042718434048712, -5.30122089446252, 1.634935880026711, -12.638816090993133}           ,
      {74.67968720274088, -15.994227102594671, -91.98588304792695, 6.223597911923417, 2.610699787250417, -122.45408791174088, -22.572278598498198, 58.96409111020579, 48.01212370431544, 52.79188876877087, -122.21689228311237, 63.061991190283415, 32.793588143039315, -82.43810980711379, -107.4129571336538, -52.53737038403699, 84.77909410891544, 13.323538848558933, 6.802740222052576, 123.10416430643502, 39.7938142464809, 14.250757994996382, -3.080543844518305, 39.8286749351633}                    ,
      {-195.12717789241353, 43.09240112708885, 240.55808089969315, -16.74674230927075, -5.473133045823825, 318.15883511734626, 60.00849981887953, -155.35671776490042, -126.05853729530409, -138.134965770583, 320.9784271236896, -164.76797570717838, -85.05173255849297, 215.73812822592927, 281.77839747518806, 137.20671141068644, -221.44359479126996, -35.13774685203168, -17.608572898768777, -322.77703414263357, -103.711825933615, -38.63612213931014, 8.309487145361178, -104.41988703304348}          ,
      {108.4490974833159, -24.623029820425554, -133.4319545913671, 9.939122888092667, 3.5853506541334768, -176.4166240524181, -33.43092906302204, 86.1055088365584, 70.22191144066349, 76.44882779014183, -178.48106825583625, 91.86543960519363, 46.90194415478151, -119.71097872368664, -156.3630175853326, -76.54636716736292, 122.80646355915381, 20.26664962500423, 9.981183121826895, 179.33059064753377, 57.30492066250239, 21.36731790048276, -4.97400037934499, 57.39478000766156}                       ,
      {-20.14976901547902, 4.176322545572981, 25.53568916489614, -1.3319055309403942, -0.7855120025928377, 35.32156832561052, 6.440802327758392, -16.354517494808363, -12.11246776832715, -15.106900645527302, 33.081834041200096, -17.294543274762322, -9.6489663512073, 22.636207036188154, 29.199110791212462, 14.204527991680228, -24.477882003638253, -3.3537073589184407, -2.2069422284753686, -33.510465420330455, -11.45974803698687, -3.404768249968973, 0.9496974498944343, -11.784578662593931}        ,
      {87.50027697280268, -18.487725091020415, -108.39506561377665, 7.247998856281592, 2.30149917066365, -144.50501874249494, -26.65324328616678, 69.87702152963375, 55.28307442333029, 62.42774550534523, -143.8694462769741, 74.19884477837813, 38.62020597172227, -97.26048865307085, -126.65379762566383, -60.90503651456335, 100.74735964550011, 15.260310533644798, 7.870903471291747, 144.78561994399135, 47.37019019507242, 16.395245184409912, -2.8788810414040342, 47.53034983556162}                   ,
      {113.45541985368041, -25.975953441816415, -137.8628994000321, 10.214416038075107, 2.583366782619242, -181.24535628918326, -34.75314327725714, 89.9637883159295, 73.79938354730606, 78.35219907274563, -184.76955488132197, 94.77133892249387, 48.14752603941986, -123.5322597604569, -161.53868867218384, -79.31047296330294, 126.85395891381862, 20.90121526262501, 8.996015451814253, 186.37919522173289, 58.82680563183301, 22.41798725499781, -6.120979532169101, 59.15406594920787}                    ,
      {82.85535361450357, -18.370823094148303, -102.23138866054784, 7.299481978506499, 2.905846098548575, -135.85909810854227, -25.959081303562513, 65.42400501258439, 54.22112265577456, 59.31511110219085, -136.62247846174196, 70.02789505759175, 36.264419786209494, -91.25987587395828, -119.96321991509853, -58.58067043297318, 94.47658950837977, 15.736224692554481, 7.292796470936269, 136.89398051901492, 44.01238908284462, 16.318273364385536, -3.856162405528518, 44.30556497004376}                 ,
      {17.864083680797027, -3.5127102126610934, -22.655814571063537, 1.8729435973858508, 1.375020143094102, -32.33601756807391, -5.612451875861593, 13.624102266258673, 10.774177321944462, 14.034766594634817, -29.840591386930658, 15.474879062088913, 8.639138136219326, -20.69827269358353, -27.327965770380388, -12.680642751455082, 21.942805237589923, 2.781883411077388, 2.5861362633357334, 29.499509653879542, 10.621471049701455, 3.295064203023629, -0.03513791774338615, 10.913295305307209}         ,
      {38.771897815953196, -8.98910748530257, -47.92048249847497, 3.7736470887890436, 1.6423893718002585, -64.60289629413722, -12.294521539482687, 30.944650150408357, 25.62180476013203, 28.374967091813563, -65.15131838189636, 32.901244741062015, 17.373123355528843, -43.24068834085599, -56.99864302007738, -27.627256078974693, 44.071643595059136, 7.346379381733037, 3.9740137668724396, 65.36432815959093, 21.056451800947155, 7.747965700933956, -1.680717798164576, 20.884418585818946}               ,
      {-16.529068526621852, 4.772326788206213, 18.4486784475041, -1.4136995461069664, -0.602218685637782, 23.18433483090613, 5.100001437610258, -13.24735955296475, -11.342718623630557, -9.90384075808147, 26.309164281829705, -13.726107974845728, -6.076334045715075, 17.145124125793163, 22.774788244829313, 11.693228513045213, -16.84567157271961, -2.895350288673774, -1.140379170556996, -26.67553475725454, -6.85481165535994, -3.6629202419164195, 1.4968781420604347, -7.471834650248002}              ,
      {-114.8318709047158, 25.370061474713374, 142.25308047623182, -10.008900360679187, -3.5515994718776316, 188.01792942917714, 35.42655908318452, -90.71100937279303, -73.47207546225944, -81.81058854904968, 188.54149606710362, -97.50781535197588, -49.92011635296421, 127.177240710548, 165.98779265152183, 79.96508338056988, -130.73995926063725, -20.560012252252836, -10.639489825603306, -189.7455814796144, -61.552643189978454, -22.222599543384383, 4.808118997430446, -62.34283076822591}          ,
      {-2.4229289500190387, -0.1396959940448339, 3.7402616456748436, -0.12503452336471754, -0.5330173648687013, 6.704406277553223, 1.244914181294908, -1.6892773327554536, -1.220724489702142, -3.6005495375138685, 4.474405278885632, -2.163890026876697, -1.4313623342301878, 3.2713266729744115, 4.2357680196415135, 1.8537291686518675, -3.746925211300896, -0.8810448663790106, -0.6235630456190191, -4.1558321103737335, -2.2075952629594244, 0.01873363783704729, -0.6921227459303223, -1.8191739894840664},
      {-61.768658905620896, 13.886921346659747, 76.37255894297687, -5.484337561696297, -1.5618850729226046, 101.54324662861814, 18.71872701052836, -49.31234173137113, -39.787033872577524, -43.66563000724196, 102.60004896150951, -52.60596891288826, -27.2637761474418, 68.85043018070763, 89.7969801435592, 43.50316768615088, -70.67795106403914, -10.007284447907917, -6.132308593032078, -103.47219558850783, -33.01091880415913, -12.160687771279441, 1.8489663374058385, -33.87569750632986}             ,
      {22.147837370751404, -4.206046674944264, -28.253303049522046, 1.346910089877908, 0.6649259321871875, -38.02374402952911, -6.893545229381181, 18.38955053502488, 14.277394110655454, 16.103388751217146, -36.901733793719046, 19.5912994122686, 9.925810853262872, -25.56976977078336, -32.990061866540614, -15.760288220222343, 26.279705293249393, 3.30286220760752, 1.8995597698396982, 37.54494979327625, 12.506644245339604, 4.74342473113152, -0.030113327207158758, 12.42064394037889}                ,
      {-178.94972020476558, 40.55396135025187, 219.17717138541133, -15.943850246699371, -5.8789079895603304, 290.83952973504455, 54.80443917058063, -142.15732233598874, -115.79098035559262, -125.87502583660222, 293.1440666259708, -150.6709034130114, -77.67021250675018, 196.7850378149412, 257.728311392011, 126.20396024465174, -202.49760960664992, -33.01270476381034, -15.632675720087281, -295.12400625653964, -93.93160770804131, -34.97613830487525, 8.641896603941541, -95.07311553627228}          ,
      {26.040991090422935, -6.157429320400622, -31.58502955363948, 2.2333772236805434, 0.825247722330195, -40.403133663060395, -7.463090209430625, 20.463445693762278, 16.65460077210635, 17.04916329923863, -43.02343993378179, 21.60329823001591, 10.630153018664043, -28.54628568953352, -37.48313101525145, -18.149149001814273, 28.260785734038457, 3.890268814941191, 3.085146853483592, 43.40336161156118, 13.056208326604144, 5.800367403121667, -0.7090505590988079, 14.172306536199155}                 ,
      {-261.3564526110459, 57.71138689555388, 322.2299128649491, -22.836538249828383, -7.3599481903852055, 426.6257530763272, 79.8892885404916, -207.26878451068373, -168.76559726904605, -184.43221847737496, 429.2332697969281, -220.82101065217185, -113.63268692956076, 288.6013787586168, 377.3867531300932, 183.09434856493155, -297.25076493541064, -47.2657215425273, -23.866113454894936, -432.3285367397767, -138.78979802785435, -50.93564236936191, 11.386456392288471, -139.86055314350807}          ,
      {99.6924619968757, -22.424263512180776, -122.07820401414646, 8.229322216540865, 2.691038459954785, -161.541639716045, -30.63795417366823, 79.67997589953083, 64.17652603521483, 70.1044028318318, -163.11256371684286, 84.26911644687259, 43.23736767107092, -109.8401003324629, -143.39096151884115, -70.63786063255331, 113.35609919806383, 17.916162347827715, 8.408188910101432, 165.06397064680027, 52.28120828901116, 19.612058998922127, -4.683792048889328, 52.72339813682203}                      ,
      {72.13345890851818, -16.80086811298244, -88.62097419286377, 6.766992726628969, 1.8858270810033633, -115.5497141460214, -21.78336696447696, 57.29786242458489, 47.13595360736241, 50.1610116217027, -118.6052462495557, 60.61474130696486, 30.852603360843066, -79.16310162792082, -103.88502884903667, -50.251606779739575, 80.86138674835621, 12.84541178619756, 6.71024184064062, 119.4718400675313, 37.297816592393545, 14.398014507049886, -3.309923124993538, 37.95931259186417}                       ,
    };
//@formatter:on

    this.assertEquals(expected, inverse);
    // Check whether multiplication leads to identity.
    Assert.assertTrue(this.isIdentity(this.calc.multiply(new Matrix(data), inverse)));
  }
}
