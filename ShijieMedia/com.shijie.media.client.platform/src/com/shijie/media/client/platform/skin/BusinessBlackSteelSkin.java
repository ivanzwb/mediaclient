package com.shijie.media.client.platform.skin;

import org.pushingpixels.substance.api.SubstanceConstants.ImageWatermarkKind;
import org.pushingpixels.substance.api.watermark.SubstanceImageWatermark;

import com.shijie.media.client.api.ui.ISkin;

public class BusinessBlackSteelSkin extends org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin implements ISkin{
	public BusinessBlackSteelSkin(){
		super();
		SubstanceImageWatermark imageWatermark = new SubstanceImageWatermark(this.getClass().getResourceAsStream("/ICON-INF/watermark.jpg"));
		imageWatermark.setOpacity(0.5f);
		imageWatermark.setKind(ImageWatermarkKind.APP_ANCHOR);
		this.watermark = imageWatermark;
	}
	
	public String getDisplayName(){
		return "经典黑金属";
	}
	
	public String getSkinId(){
		return this.getClass().getName();
	}
}
