
public class ColorConverter {
	/**
	 * A function that converts a CMY color value to a RGB color value
	 * @param f an array that contains the CMY color value
	 * @return an array that contains the RGB color value
	 */
	//this one expects me to give color in 0-1 range and returns in 0-1 range
	public static float[] CMY2RGB(float[] f) {
		float r=255*(1-f[0]);
		float g =255*(1-f[1]);
		float b=255*(1-f[2]);
		f[0]=r/255;
		f[1]=g/255;
		f[2]=b/255;
		return f;
	}
	/**
	 * A function that converts a HSV color value to a RGB color value
	 * @param f an array that contains the HSV color value
	 * @return an array that contains the RGB color value
	 */
	//this one expects me to give color in 0-255 range and returns in 0-1 range
	public static float[] HSV2RGB(float[] f) {
		float r=0,g=0,b=0;
		float max=f[2];
		float min=max*(1-f[1]);
		while(f[0]<0) {
			f[0]+=360;
		}
		int ih=(int)Math.floor(f[0]/60);
		float alpha=f[0]/60-ih;
		if(ih%2==1)
			alpha=1-alpha;
		float mid=min+alpha*(max-min);
		switch(ih){
		case 0:r=max;g=mid;b=min;break;
		case 1:g=max;r=mid;b=min;break;
		case 2:g=max;b=mid;r=min;break;
		case 3:b=max;g=mid;r=min;break;
		case 4:b=max;r=mid;g=min;break;
		case 5:r=max;b=mid;g=min;break;
		}
		f[0]=r/255;
		f[1]=g/255;
		f[2]=b/255;
		return f;
	}
}
