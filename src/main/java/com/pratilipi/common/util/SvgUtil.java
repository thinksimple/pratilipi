package com.pratilipi.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pratilipi.common.exception.UnexpectedServerException;


public class SvgUtil {

	public static final String svgPatternString = "<svg.+?(width=\"[0-9]*\").+?(height=\"[0-9]*\").+?>";

	private static byte[] _resizeSvg( String svg, Integer width, Integer height ) {

		Pattern pattern = Pattern.compile( svgPatternString );
		Matcher m = pattern.matcher( svg );

		if( m.find() ) {
			String line = m.group(0);
			String w = m.group(1).trim();
			String h = m.group(2).trim();
			String newLine = line.replaceFirst( Pattern.quote(w), "width=\"" + width + "\"" );
			newLine = newLine.replaceFirst( Pattern.quote(h), "height=\"" + height + "\"" );
			svg = svg.replaceFirst( Pattern.quote( line ), newLine );
		}

		return svg.getBytes();

	}

	public static byte[] resizeSvg( byte[] svgData, Integer width ) throws UnexpectedServerException {

		try {

			String svg = new String( svgData, "UTF-8" );
			Pattern pattern = Pattern.compile( svgPatternString );
			Matcher m = pattern.matcher( svg );

			if( m.find() ) {
				String w = m.group(1).trim();
				String h = m.group(2).trim();
				Integer widthInt = Integer.parseInt( w.substring( w.indexOf( "\"" ) + 1, w.lastIndexOf( "\"" ) ) );
				Integer heightInt = Integer.parseInt( h.substring( h.indexOf( "\"" ) + 1, h.lastIndexOf( "\"" ) ) );
				Double hwRatio = (double) heightInt/widthInt;
				return _resizeSvg( svg, width, (int)Math.round( width * hwRatio ) );
			}

			return svgData;

		} catch( UnsupportedEncodingException e ) {
			throw new UnexpectedServerException();
		}

	}

	public static byte[] resizeSvg( byte[] svgData, Integer width, Integer height ) throws UnexpectedServerException {

		try {
			return _resizeSvg( new String( svgData, "UTF-8" ), width, height );

		} catch( UnsupportedEncodingException e ) {
			throw new UnexpectedServerException();
		}

	}

	public static Integer getWidth( byte[] svgData ) throws UnexpectedServerException {

		try {

			String svg = new String( svgData, "UTF-8" );
			Pattern pattern = Pattern.compile( svgPatternString );
			Matcher m = pattern.matcher( svg );

			if( m.find() ) {
				String w = m.group(1).trim();
				return Integer.parseInt( w.substring( w.indexOf( "\"" ) + 1, w.lastIndexOf( "\"" ) ) );
			}

			return null;

		} catch( UnsupportedEncodingException e ) {
			throw new UnexpectedServerException();
		}

	}

	public static Integer getHeight( byte[] svgData ) throws UnexpectedServerException {

		try {

			String svg = new String( svgData, "UTF-8" );
			Pattern pattern = Pattern.compile( svgPatternString );
			Matcher m = pattern.matcher( svg );

			if( m.find() ) {
				String h = m.group(2).trim();
				return Integer.parseInt( h.substring( h.indexOf( "\"" ) + 1, h.lastIndexOf( "\"" ) ) );
			}

			return null;

		} catch( UnsupportedEncodingException e ) {
			throw new UnexpectedServerException();
		}

	}

}