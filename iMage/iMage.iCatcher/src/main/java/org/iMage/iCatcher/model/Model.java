package org.iMage.iCatcher.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDrize;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;
import org.iMage.HDrize.base.images.HDRImageIO;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.iMage.iCatcher.util.IObserver;

/**
 * The implementation of the {@link IModel} of MVC.
 *
 * @author Dominik Fuchss
 *
 */
final class Model implements IModel {
  private final Set<IObserver<? super IModel>> observers = new HashSet<>();

  private CurveMode cm = CurveMode.StandardCurve;
  private float lambda = 20;
  private int samples = 500;
  private ToneMapping mapping = ToneMapping.SRGBGamma;

  private String curveLoadedFromFileName;
  private ICameraCurve loadedCameraCurve;

  private ICameraCurve calculatedCurve;
  private float lambda4calculatedCurve;
  private int samples4calculatedCurve;

  private ICameraCurve standardCurve = new CameraCurve();

  private String lcp;
  private EnhancedImage[] input;
  private BufferedImage[] inputRGB;

  private HDRImage result;
  private Map<ToneMapping, BufferedImage> resultRGB;

  @Override
  public CurveMode getCurveMode() {
    return this.cm;
  }

  @Override
  public void registerObserver(IObserver<? super IModel> observer) {
    this.observers.add(observer);
    this.informObservers();
  }

  @Override
  public void informObservers() {
    for (IObserver<? super IModel> observer : this.observers) {
      observer.invokeUpdate(this);
    }
  }

  @Override
  public void setCameraCurveMode(CurveMode cameraCurveMode) {
    if (this.cm == cameraCurveMode || cameraCurveMode == null) {
      return;
    }
    this.cm = Objects.requireNonNull(cameraCurveMode);
    this.result = null;
    this.resultRGB = null;
    this.informObservers();
  }

  @Override
  public float getLambda() {
    return this.lambda;
  }

  @Override
  public boolean setLambda(float lambda) {
    if (this.lambda == lambda) {
      return true;
    }

    if (lambda <= 0 || lambda > 100) {
      return false;
    }
    this.lambda = lambda;
    if (this.cm == CurveMode.CalculatedCurve) {
      this.result = null;
      this.resultRGB = null;
    }
    this.informObservers();
    return true;
  }

  @Override
  public int getSamples() {
    return this.samples;
  }

  @Override
  public boolean setSamples(int samples) {
    if (this.samples == samples) {
      return true;
    }
    if (samples < 1 || samples > 1000) {
      return false;
    }

    this.samples = samples;
    if (this.cm == CurveMode.CalculatedCurve) {
      this.result = null;
      this.resultRGB = null;
    }
    this.informObservers();
    return true;
  }

  @Override
  public void setMapping(ToneMapping mapping) {
    if (mapping == null) {
      return;
    }
    if (this.mapping == mapping) {
      return;
    }
    this.mapping = mapping;
    this.informObservers();
  }

  @Override
  public void setInput(String longestCommenPrefix, BufferedImage[] originals,
      EnhancedImage[] images) {
    this.lcp = longestCommenPrefix;
    this.input = images;
    this.inputRGB = originals;
    this.calculatedCurve = null;
    this.result = null;
    this.resultRGB = null;
    this.informObservers();
  }

  @Override
  public void loadCurve(String name, CameraCurve cc) {
    this.loadedCameraCurve = cc;
    this.curveLoadedFromFileName = name;
    this.cm = CurveMode.LoadedCurve;
    this.informObservers();
  }

  @Override
  public BufferedImage[] getInput() {
    return this.inputRGB;
  }

  @Override
  public String getCommonPrefix() {
    return this.lcp;
  }

  @Override
  public BufferedImage getResult() {
    if (this.result == null) {
      return null;
    }
    if (this.resultRGB != null) {
      return this.resultRGB.get(this.mapping);
    }
    Map<ToneMapping, BufferedImage> rgbs = new HashMap<>();
    for (ToneMapping tm : ToneMapping.values()) {
      rgbs.put(tm, HDRImageIO.createRGB(this.result, tm));
    }

    this.resultRGB = rgbs;
    return this.resultRGB.get(this.mapping);
  }

  @Override
  public boolean runHdr() {
    ICameraCurve cc = this.createCurve();
    if (!cc.isCalculated()) {
      cc.calculate();
    }

    if (!cc.isCalculated()) {
      return false;
    }

    IHDrize<?> hdrize = new HDrize();
    this.result = hdrize.createHDR(this.input, cc);
    this.informObservers();
    return this.result != null;
  }

  private ICameraCurve createCurve() {
    switch (this.cm) {
    case StandardCurve:
      return this.standardCurve;
    case CalculatedCurve:
      if (this.calculatedCurve != null && this.lambda4calculatedCurve == this.lambda
          && this.samples4calculatedCurve == this.samples) {
        return this.calculatedCurve;
      } else {
        this.lambda4calculatedCurve = this.lambda;
        this.samples4calculatedCurve = this.samples;
        this.calculatedCurve = this.input == null ? null
            : new CameraCurve(this.input, this.samples, this.lambda, new MatrixCalculator());
        return this.calculatedCurve;
      }
    case LoadedCurve:
      return this.loadedCameraCurve;
    default:
      throw new Error("Internal Error: " + this.cm + " is not known!");
    }

  }

  @Override
  public ICameraCurve getCurve() {
    switch (this.cm) {
    case StandardCurve:
      return this.standardCurve;
    case CalculatedCurve:
      if (this.calculatedCurve != null && this.lambda4calculatedCurve == this.lambda
          && this.samples4calculatedCurve == this.samples) {
        return this.calculatedCurve;
      } else {
        return null;
      }
    case LoadedCurve:
      return this.loadedCameraCurve;
    default:
      throw new Error("Internal Error: " + this.cm + " is not known!");
    }

  }

  @Override
  public String getCurveName() {
    switch (this.cm) {
    case StandardCurve:
      return "Standard Curve";
    case CalculatedCurve:
      return String.format("Calculated Curve (%ds, %.2fl)", this.samples, this.lambda);
    case LoadedCurve:
      return "Loaded Curve (" + this.curveLoadedFromFileName + ")";
    default:
      throw new Error("Internal Error: " + this.cm + " is not known!");
    }
  }

  @Override
  public boolean isCurveLoaded() {
    return this.loadedCameraCurve != null;
  }
}
