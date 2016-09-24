package com.pratilipi.data.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.CommentState;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.ReferenceType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.VoteParentType;
import com.pratilipi.common.type.VoteType;
import com.pratilipi.common.util.GoogleAnalyticsApi;
import com.pratilipi.common.util.HttpUtil;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.UserPratilipiDoc;
import com.pratilipi.data.type.Vote;


public class PratilipiDocUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiDocUtil.class.getName() );
	
	
	public static void updatePratilipiContent( Long pratilipiId ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		PratilipiContentDoc pcDoc = DataAccessorFactory.getDocAccessor().newPratilipiContentDoc();
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
		
			BlobEntry blobEntry = blobAccessor.getBlob( "pratilipi-content/pratilipi/" + pratilipiId );
			if( blobEntry == null )
				return;
			String contentHtml = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			
			List<Object[]> pageletList = _createPageletList( pratilipi, Jsoup.parse( contentHtml ).body() );
			
			if( pageletList.size() > 0 ) {
				PratilipiContentDoc.Chapter chapter = null;
				if( pageletList.get( 0 )[0] != PratilipiContentDoc.PageletType.HEAD_1 )
					chapter = pcDoc.addChapter( pratilipi.getTitle() == null ? pratilipi.getTitleEn() : pratilipi.getTitle() );
				
				for( Object[] pagelet : pageletList ) {
					if( pagelet[0] == PratilipiContentDoc.PageletType.HEAD_1 )
						chapter = pcDoc.addChapter( (String) pagelet[1] );
					else if( pagelet[0] == PratilipiContentDoc.PageletType.HEAD_2 )
						chapter = pcDoc.addChapter( (String) pagelet[1], 1 );
					else if( chapter.getPage( 1 ) == null )
						chapter.addPage( (PratilipiContentDoc.PageletType) pagelet[0], pagelet[1] );
					else
						chapter.getPage( 1 ).addPagelet( (PratilipiContentDoc.PageletType) pagelet[0], pagelet[1] );
				}
			}
			
		} else if( pratilipi.getContentType() == PratilipiContentType.IMAGE ) {
			
			for( int i = 1; i <= pratilipi.getPageCount(); i++ ) {
				
				BlobEntry blobEntry = blobAccessor.getBlob( "pratilipi-content/image/" + pratilipiId + "/" + i );
				
				JsonObject imgData = new JsonObject();
				imgData.addProperty( "name", i + "" );
				imgData.addProperty( "height", ImageUtil.getHeight( blobEntry.getData() ) );
				imgData.addProperty( "width", ImageUtil.getWidth( blobEntry.getData() ) );
				
				PratilipiContentDoc.Chapter chapter = pcDoc.addChapter( null );
				chapter.addPage( PratilipiContentDoc.PageletType.IMAGE, imgData );
				
			}
			
		}
		
		docAccessor.save( pratilipiId, pcDoc );
		
	}
	
	private static List<Object[]> _createPageletList( Pratilipi pratilipi, Node node ) throws UnexpectedServerException {
		
		List<Object[]> pageletList = new LinkedList<>();
		
		Node prevNode = null;
		Object[] pagelet = null;
		for( Node childNode : node.childNodes() ) {
			
			if( childNode.nodeName().equals( "body" )
					|| childNode.nodeName().equals( "div" )
					|| childNode.nodeName().equals( "p" ) ) {
				
				pagelet = null;
				pageletList.addAll( _createPageletList( pratilipi, childNode ) );
				
			} else if( childNode.nodeName().equals( "h1" ) ) {
				
				String text = _extractText( childNode );
				if( text == null )
					continue;
				if( pagelet != null && ( pagelet[0] == PratilipiContentDoc.PageletType.HEAD_1 || pagelet[0] == PratilipiContentDoc.PageletType.HEAD_2 ) ) {
					pagelet[1] = pagelet[1] + " - " + text;
				} else {
					pagelet = new Object[] { PratilipiContentDoc.PageletType.HEAD_1, text };
					pageletList.add( pagelet );
				}
				
			} else if( childNode.nodeName().equals( "h2" ) ) {
				
				String text = _extractText( childNode );
				if( text == null )
					continue;
				if( pagelet != null && pagelet[0] == PratilipiContentDoc.PageletType.HEAD_2 ) {
					pagelet[1] = pagelet[1] + " - " + text;
				} else {
					pagelet = new Object[] { PratilipiContentDoc.PageletType.HEAD_2, text };
					pageletList.add( pagelet );
				}
				
			} else if( childNode.nodeName().equals( "img" ) ) {
				
				pagelet = null;
				
				BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
				BlobEntry blobEntry = null;
				String imageUrl = childNode.attr( "src" );
				String imageName = null;
				if( imageUrl.indexOf( "name=" ) != -1 ) {
					imageName = imageUrl.substring( imageUrl.indexOf( "name=" ) + 5 );
					if( imageName.indexOf( '&' ) != -1 )
						imageName = imageName.substring( 0, imageName.indexOf( '&' ) );
					imageName = imageName.replace( "%20", " " );
					String fileName = _createImageFullName( pratilipi.getId(), imageName );
					blobEntry = blobAccessor.getBlob( fileName );
					if( blobEntry == null ) // TODO: Remove this when all images from old resource folder are migrated to new resource location
						blobEntry = blobAccessor.getBlob( "pratilipi-resource/" + pratilipi.getId() + "/" + imageName );
					if( blobEntry == null && imageUrl.indexOf( "pratilipiId=" ) != -1 ) { // TODO: Remove this when all images from old resource folder are migrated to new resource location
						String pratilipiIdStr = imageUrl.substring( imageUrl.indexOf( "pratilipiId=" ) + 12 );
						if( pratilipiIdStr.indexOf( '&' ) != -1 )
							pratilipiIdStr = pratilipiIdStr.substring( 0, pratilipiIdStr.indexOf( '&' ) );
						blobEntry = blobAccessor.getBlob( "pratilipi-resource/" + pratilipiIdStr + "/" + imageName );
						blobEntry.setName( fileName );
						blobAccessor.createOrUpdateBlob( blobEntry );
					}
				} else if( imageUrl.startsWith( "http" ) ) {
					imageName = imageUrl.replaceAll( "[:/.?=&+]+", "_" );
					String fileName = _createImageFullName( pratilipi.getId(), imageName );
					blobEntry = blobAccessor.getBlob( fileName );
					if( blobEntry == null ) {
						blobEntry = HttpUtil.doGet( imageUrl );
						if( ! blobEntry.getMimeType().startsWith( "image/" ) )
							continue;
						blobEntry.setName( fileName );
						blobAccessor.createOrUpdateBlob( blobEntry );
					}
				} else if( imageUrl.startsWith( "data:" ) && imageUrl.indexOf( "base64" ) != -1 ) {
					imageName = UUID.randomUUID().toString();
					String mimeType = imageUrl.substring( 5, imageUrl.indexOf( ';' ) );
					String base64String = imageUrl.substring( imageUrl.indexOf( "base64," ) + 7 );
					blobEntry = blobAccessor.newBlob(
							_createImageFullName( pratilipi.getId(), imageName ),
							Base64.decodeBase64( base64String ),
							mimeType );
					blobAccessor.createOrUpdateBlob( blobEntry );
				} else if( imageUrl.startsWith( "file:///" ) || imageUrl.startsWith( "C:" ) ) {
					continue;
				}
				
				JsonObject imgData = new JsonObject();
				imgData.addProperty( "name", imageName );
				imgData.addProperty( "height", ImageUtil.getHeight( blobEntry.getData() ) );
				imgData.addProperty( "width", ImageUtil.getWidth( blobEntry.getData() ) );
				
				pageletList.add( new Object[] { PratilipiContentDoc.PageletType.IMAGE, imgData } );
				
			} else if( childNode.nodeName().equals( "br" ) ) {
				
				// Create new pagelet after 2 consecutive line breaks.
				if( prevNode.nodeName().equals( "br" ) )
					pagelet = null;
				
			} else {
				
				String text  = _extractText( childNode );
				if( text == null )
					continue;
				if( pagelet == null || pagelet[0] != PratilipiContentDoc.PageletType.TEXT ) {
					pagelet = new Object[] { PratilipiContentDoc.PageletType.TEXT, text };
					pageletList.add( pagelet );
				} else if( prevNode.nodeName().equals( "br" ) ) {
					pagelet[1] = pagelet[1] + "\n" + text;
				} else {
					pagelet[1] = pagelet[1] + " " + text;
				}
				
			}
			
			
			prevNode = childNode;
			
		}
		
		return pageletList;
		
	}
	
	private static String _createImageFullName( Long pratilipiId, String imageName ) {
		return "pratilipi/" + pratilipiId + "/images/" + imageName;
	}
	
	private static String _extractText( Node node ) {
		if( node.getClass() == org.jsoup.nodes.Comment.class )
			return null;
		String text = node.getClass() == TextNode.class
				? ( (TextNode) node ).text()
				: ( (Element) node ).text();
		text = text.replace( (char) 160, ' ' ).trim();
		return text.isEmpty() ? null : text;
	}
	
	
	public static void updatePratilipiReviews( Long pratilipiId ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		List<Comment> commentList = dataAccessor.getCommentListByReference( ReferenceType.PRATILIPI, pratilipiId );
		Map<String, List<Comment>> reviewIdCommentListMap = new HashMap<>();
		for( Comment comment : commentList ) {
			if( comment.getParentType() != CommentParentType.REVIEW )
				continue;
			List<Comment> reviewCommentList = reviewIdCommentListMap.get( comment.getParentId() );
			if( reviewCommentList == null ) {
				reviewCommentList = new LinkedList<>();
				reviewIdCommentListMap.put( comment.getParentId(), reviewCommentList );
			}
			reviewCommentList.add( comment );
		}
		
		List<Vote> voteList = dataAccessor.getVoteListByReference( ReferenceType.PRATILIPI, pratilipiId );
		Map<String, List<Vote>> reviewIdLikeVotesMap = new HashMap<>();
		Map<String, List<Long>> commentIdLikedByUserIdsMap = new HashMap<>();
		for( Vote vote : voteList ) {
			if( vote.getType() == VoteType.NONE ) {
				continue;
			} else if( vote.getParentType() == VoteParentType.REVIEW ) {
				List<Vote> reviewLikeVoteList = reviewIdLikeVotesMap.get( vote.getParentId() );
				if( reviewLikeVoteList == null ) {
					reviewLikeVoteList = new LinkedList<>();
					reviewIdLikeVotesMap.put( vote.getParentId(), reviewLikeVoteList );
				}
				reviewLikeVoteList.add( vote );
			} else if( vote.getParentType() == VoteParentType.COMMENT ) {
				List<Long> userIdList = commentIdLikedByUserIdsMap.get( vote.getParentId() );
				if( userIdList == null ) {
					userIdList = new LinkedList<>();
					commentIdLikedByUserIdsMap.put( vote.getParentId(), userIdList );
				}
				userIdList.add( vote.getUserId() );
			}
		}
		
		
		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( null, pratilipiId, null, null ).getDataList();
		long ratingCount = 0;
		long totalRating = 0;
		List<UserPratilipiDoc> reviewDocList = new ArrayList<>();
		for( UserPratilipi userPratilipi : userPratilipiList ) {
			
			if( userPratilipi.getRating() != null && userPratilipi.getRating() > 0 ) {
				ratingCount++;
				totalRating += userPratilipi.getRating();
			}
			
			if( userPratilipi.getReviewState() != UserReviewState.PUBLISHED )
				continue;
			
			if( ( userPratilipi.getReviewTitle() == null || userPratilipi.getReviewTitle().trim().isEmpty() )
					&& ( userPratilipi.getReview() == null || userPratilipi.getReview().trim().isEmpty() ) )
				continue;
			
			UserPratilipiDoc reviewDoc = docAccessor.newUserPratilipiDoc();
			reviewDoc.setId( userPratilipi.getId() );
			reviewDoc.setUserId( userPratilipi.getUserId() );

			reviewDoc.setRating( userPratilipi.getRating() );
			reviewDoc.setReviewTitle( userPratilipi.getReviewTitle() == null || userPratilipi.getReviewTitle().trim().isEmpty() ? null : userPratilipi.getReviewTitle().trim() );
			reviewDoc.setReview( userPratilipi.getReview() == null || userPratilipi.getReview().trim().isEmpty() ? null : userPratilipi.getReview().trim() );
			
			reviewDoc.setReviewDate( userPratilipi.getReviewDate() );
			reviewDocList.add( reviewDoc );
			
			
			List<Vote> reviewLikeVoteList = reviewIdLikeVotesMap.get( userPratilipi.getId() );
			if( reviewLikeVoteList != null ) {
				List<Long> userIdList = new ArrayList<>( reviewLikeVoteList.size() );
				for( Vote vote : reviewLikeVoteList ) {
					if( vote.getLastUpdated().before( userPratilipi.getReviewDate() ) )
						continue;
					userIdList.add( vote.getUserId() );
				}
				reviewDoc.setLikedByUserIds( userIdList );
			}
			
			
			List<Comment> reviewCommentList = reviewIdCommentListMap.get( userPratilipi.getId() );
			if( reviewCommentList != null ) {
				List<CommentDoc> commentDocList = new ArrayList<>( reviewCommentList.size() );
				for( Comment comment : reviewCommentList ) {
					if( comment.getState() == CommentState.DELETED )
						continue;
					
					if( comment.getCreationDate().before( userPratilipi.getReviewDate() ) )
						continue;
					
					CommentDoc commentDoc = docAccessor.newCommentDoc();
					commentDoc.setId( comment.getId() );
					commentDoc.setUserId( comment.getUserId() );
					commentDoc.setContent( comment.getContent() );
					commentDoc.setCreationDate( comment.getCreationDate() );
					commentDoc.setLastUpdated( comment.getLastUpdated() );
					commentDoc.setLikedByUserIds( commentIdLikedByUserIdsMap.get( comment.getId().toString() ) );
					
					commentDocList.add( commentDoc );
				}
				reviewDoc.setComments( commentDocList );
			}
			
		}
		
		PratilipiReviewsDoc reviewsDoc = docAccessor.newPratilipiReviewsDoc();
		reviewsDoc.setRatingCount( ratingCount );
		reviewsDoc.setTotalRating( totalRating );
		reviewsDoc.setReviews( reviewDocList );
		docAccessor.save( pratilipiId, reviewsDoc );
		
	}
	
	
 	public static List<Long> updatePratilipiGoogleAnalyticsPageViews( int year, int month, int day )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		Gson gson = new Gson();
		
		String dateStr = year
				+ ( month < 10 ? "-0" + month : "-" + month )
				+ ( day < 10 ? "-0" + day : "-" + day );
		
		String fileName = "pratilipi-google-analytics/page-views/" + dateStr;
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		if( blobEntry == null ) {
			try {
				blobEntry = blobAccessor.newBlob( fileName, "{}".getBytes( "UTF-8" ), "application/json" );
			} catch( UnsupportedEncodingException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}
		
		
		@SuppressWarnings("serial")
		Map<String, Integer> oldPageViewsMap = gson.fromJson(
				new String( blobEntry.getData(), Charset.forName( "UTF-8" ) ),
				new TypeToken<Map<String, Integer>>(){}.getType() );
		
		Map<String, Integer> newPageViewsMap =
				GoogleAnalyticsApi.getPageViews( dateStr );
		
		Map<String, Integer> diffPageViewsMap = new HashMap<>();
		for( Entry<String, Integer> entry : newPageViewsMap.entrySet() )
			if( ! entry.getValue().equals( oldPageViewsMap.get( entry.getKey() ) ) )
				diffPageViewsMap.put( entry.getKey(), entry.getValue() );
		
		
		Map<Long, Integer> pageViewsMap = new HashMap<>();
		Map<Long, Integer> readPageViewsMap = new HashMap<>();
		
		for( Entry<String, Integer> entry : diffPageViewsMap.entrySet() ) {
			
			String uri = entry.getKey();
			
			if( ! uri.startsWith( "/read?id=" ) ) { // Summary Page
				
				if( uri.indexOf( '?' ) != -1 )
					uri = uri.substring( 0, uri.indexOf( '?' ) );
				
				Page page = dataAccessor.getPage( uri );
				if( page != null && page.getType() == PageType.PRATILIPI ) {
					Long pratilpiId = page.getPrimaryContentId();
					if( pageViewsMap.get( pratilpiId ) == null )
						pageViewsMap.put( pratilpiId, entry.getValue() );
					else
						pageViewsMap.put( pratilpiId, pageViewsMap.get( pratilpiId ) + entry.getValue() );
				}
				
			} else { // Reader
				
				String patilipiIdStr = uri.indexOf( '&' ) == -1
						? uri.substring( "/read?id=".length() )
						: uri.substring( "/read?id=".length(), uri.indexOf( '&' ) );
						
				try {
					Long pratilpiId = Long.parseLong( patilipiIdStr );
					if( readPageViewsMap.get( pratilpiId ) == null )
						readPageViewsMap.put( pratilpiId, entry.getValue() );
					else
						readPageViewsMap.put( pratilpiId, readPageViewsMap.get( pratilpiId ) + entry.getValue() );
				} catch( NumberFormatException e ) {
					logger.log( Level.SEVERE, "Exception while processing reader uri " + uri, e );
				}
				
			}
			
		}
		
		
		for( Entry<Long, Integer> entry : pageViewsMap.entrySet() ) {
			if( readPageViewsMap.get( entry.getKey() ) == null ) {
				updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), 0 );
			} else {
				updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), readPageViewsMap.get( entry.getKey() ) );
				readPageViewsMap.remove( entry.getKey() );
			}
		}
		
		for( Entry<Long, Integer> entry : readPageViewsMap.entrySet() )
			updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, 0, entry.getValue() );
		
		
		if( diffPageViewsMap.size() > 0 ) {
			try {
				blobEntry.setData( gson.toJson( newPageViewsMap ).getBytes( "UTF-8" ) );
				blobAccessor.createOrUpdateBlob( blobEntry );
			} catch( UnsupportedEncodingException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}

		
		ArrayList<Long> updatedPratilipiIdList = new ArrayList<>( pageViewsMap.size() + readPageViewsMap.size() );
		updatedPratilipiIdList.addAll( pageViewsMap.keySet() );
		updatedPratilipiIdList.addAll( readPageViewsMap.keySet() );
		
		return updatedPratilipiIdList;
		
	}
	
	public static void updatePratilipiGoogleAnalyticsPageViews( Long pratilipiId, int year, int month, int day, int pageViews, int readPageViews )
			throws UnexpectedServerException {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		PratilipiGoogleAnalyticsDoc gaDoc = docAccessor.getPratilipiGoogleAnalyticsDoc( pratilipiId );
		
		gaDoc.setPageViews( year, month, day, pageViews );
		gaDoc.setReadPageViews( year, month, day, readPageViews );

		docAccessor.save( pratilipiId, gaDoc );
		
	}
	
}
