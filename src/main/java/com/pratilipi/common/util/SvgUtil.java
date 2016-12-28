package com.pratilipi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SvgUtil {

	public static final String svgPatternString = "<svg.+?(width=\"[0-9]*\").+?(height=\"[0-9]*\").+?>"; 

	public static String resizeSvg( String svg, Integer width ) {

		Pattern pattern = Pattern.compile( svgPatternString );
		Matcher m = pattern.matcher( svg );

		if( m.find() ) {
			String w = m.group(1).trim();
			String h = m.group(2).trim();
			Integer widthInt = Integer.parseInt( w.substring( w.indexOf( "\"" ) + 1, w.lastIndexOf( "\"" ) ) );
			Integer heightInt = Integer.parseInt( h.substring( h.indexOf( "\"" ) + 1, h.lastIndexOf( "\"" ) ) );
			Double hwRatio = (double) heightInt/widthInt;
			svg = svg.replaceFirst( Pattern.quote(w), "width=\"" + width + "\"" );
			svg = svg.replaceFirst( Pattern.quote(h), "height=\"" + Math.round( width * hwRatio ) + "\"" );
		}

		return svg;

	}

	public static String resizeSvg( String svg, Integer width, Integer height ) {

		Pattern pattern = Pattern.compile( svgPatternString );
		Matcher m = pattern.matcher( svg );

		if( m.find() ) {
			String w = m.group(1).trim();
			String h = m.group(2).trim();
			svg = svg.replaceFirst( Pattern.quote(w), "width=\"" + width + "\"" );
			svg = svg.replaceFirst( Pattern.quote(h), "height=\"" + height + "\"" );
		}

		return svg;

	}

}