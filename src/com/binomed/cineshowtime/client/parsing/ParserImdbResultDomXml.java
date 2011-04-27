package com.binomed.cineshowtime.client.parsing;

import java.util.ArrayList;
import java.util.List;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ReviewBean;
import com.binomed.cineshowtime.client.model.YoutubeBean;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class ParserImdbResultDomXml extends AbstractParser {

	public static MovieBean parseResult(String response, MovieBean movie) {

		if (response == null) {
			return movie;
		}

		Document messageDom = XMLParser.parse(response);

		Node nodeMovie = messageDom.getElementsByTagName(NODE_MOVIE).item(0);

		movie.setId(getString(nodeMovie, ATTR_ID));
		movie.setImdbId(getString(nodeMovie, ATTR_IMDB_ID));
		movie.setMovieName(getString(nodeMovie, ATTR_MOVIE_NAME));
		movie.setUrlImg(getString(nodeMovie, ATTR_URL_IMG));
		movie.setUrlWikipedia(getString(nodeMovie, ATTR_URL_WIKIPEDIA));
		movie.setRate(getDouble(nodeMovie, ATTR_RATE));
		movie.setStyle(getString(nodeMovie, ATTR_STYLE));
		movie.setDirectorList(getString(nodeMovie, ATTR_DIRECTORS));
		movie.setActorList(getString(nodeMovie, ATTR_ACTORS));
		movie.setYear(getInt(nodeMovie, ATTR_YEAR));

		Node nodeDesc = ((Element) nodeMovie).getElementsByTagName(NODE_DESC).item(0);

		if (nodeDesc != null) {
			movie.setDescription(URL.decodeQueryString(((Text) nodeDesc.getFirstChild()).getData()));
			movie.setImdbDesrciption(getBoolean(nodeDesc, ATTR_IMDB_DESC));
		}

		List<ReviewBean> reviewList = new ArrayList<ReviewBean>();
		movie.setReviews(reviewList);
		List<YoutubeBean> videoList = new ArrayList<YoutubeBean>();
		movie.setYoutubeVideos(videoList);

		NodeList reviewNodeList = ((Element) nodeMovie).getElementsByTagName(NODE_REVIEW);
		Node reviewNode = null;
		ReviewBean review = null;
		for (int i = 0; i < reviewNodeList.getLength(); i++) {
			reviewNode = reviewNodeList.item(i);
			review = new ReviewBean();
			reviewList.add(review);

			review.setAuthor(getString(reviewNode, ATTR_AUTHOR));
			review.setRate(getFloat(reviewNode, ATTR_RATE));
			review.setSource(getString(reviewNode, ATTR_SOURCE));
			review.setUrlReview(getString(reviewNode, ATTR_URL_REVIEW));
			review.setReview(URL.decodeQueryString(((Text) reviewNode.getFirstChild()).getData()));
		}

		NodeList videoNodeList = ((Element) nodeMovie).getElementsByTagName(NODE_VIDEO);
		Node videoNode = null;
		YoutubeBean video = null;
		for (int i = 0; i < videoNodeList.getLength(); i++) {
			videoNode = videoNodeList.item(i);
			video = new YoutubeBean();
			videoList.add(video);

			video.setUrlImg(getString(videoNode, ATTR_URL_IMG));
			video.setUrlVideo(getString(videoNode, ATTR_URL_VIDEO));
			video.setVideoName(getString(videoNode, ATTR_VIDEO_NAME));
			video.setVideoId(getString(videoNode, ATTR_ID));
		}

		return movie;
	}
}
