package com.binomed.cineshowtime.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.binomed.cineshowtime.cst.XmlGramarNearResult;
import com.binomed.cineshowtime.model.LocalisationBean;
import com.binomed.cineshowtime.model.MovieBean;
import com.binomed.cineshowtime.model.NearResp;
import com.binomed.cineshowtime.model.ProjectionBean;
import com.binomed.cineshowtime.model.TheaterBean;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class ParserNearResultDomXml implements XmlGramarNearResult {

	public static NearResp parseResult(String response) {
		NearResp nearResp = new NearResp();

		Document messageDom = XMLParser.parse(response);

		Node nodeMovieList = messageDom.getElementsByTagName(NODE_MOVIE_LIST).item(0);

		Node nodeTheaterList = messageDom.getElementsByTagName(NODE_THEATER_LIST).item(0);

		nearResp.setMapMovies(new HashMap<String, MovieBean>());

		NodeList movieList = nodeMovieList.getChildNodes();
		Node nodeMovie = null;
		Node nodeTheaterTmp = null;
		MovieBean movieBean = null;
		NodeList theaterNodeList = null;
		for (int i = 0; i < movieList.getLength(); i++) {
			nodeMovie = movieList.item(i);
			movieBean = new MovieBean();
			movieBean.setId(((Element) nodeMovie).getAttribute(ATTR_ID));
			movieBean.setMovieName(((Element) nodeMovie).getAttribute(ATTR_MOVIE_NAME));
			movieBean.setEnglishMovieName(((Element) nodeMovie).getAttribute(ATTR_ENGLISH_MOVIE_NAME));
			movieBean.setImdbId(((Element) nodeMovie).getAttribute(ATTR_IMDB_ID));
			movieBean.setLang(((Element) nodeMovie).getAttribute(ATTR_LANG));
			movieBean.setMovieTime(Long.valueOf(((Element) nodeMovie).getAttribute(ATTR_TIME)));
			movieBean.setTheaterList(new ArrayList<String>());

			theaterNodeList = ((Element) nodeMovie).getElementsByTagName(NODE_THEATER);

			for (int j = 0; j < theaterNodeList.getLength(); j++) {
				nodeTheaterTmp = theaterNodeList.item(j);
				movieBean.getTheaterList().add(((Element) nodeTheaterTmp).getAttribute(ATTR_ID));
			}

			nearResp.getMapMovies().put(movieBean.getId(), movieBean);
		}

		NodeList theaterList = nodeTheaterList.getChildNodes();
		Node nodeTheater = null;
		Node nodeLocalisation = null;
		NodeList nodeMovieListTmp = null;
		NodeList nodeProjectionList = null;
		Node nodeMovieTmp = null;
		Node nodeProjectionTmp = null;
		TheaterBean theaterBean = null;
		List<ProjectionBean> projectionBeanList = null;
		ProjectionBean projectionBean = null;
		LocalisationBean localisationBean = null;
		for (int i = 0; i < theaterList.getLength(); i++) {
			nodeTheater = theaterList.item(i);
			theaterBean = new TheaterBean();
			theaterBean.setId(((Element) nodeTheater).getAttribute(ATTR_ID));
			theaterBean.setPhoneNumber(((Element) nodeTheater).getAttribute(ATTR_ID));
			theaterBean.setPhoneNumber(((Element) nodeTheater).getAttribute(ATTR_PHONE_NUMBER));
			theaterBean.setTheaterName(((Element) nodeTheater).getAttribute(ATTR_THEATER_NAME));
			theaterBean.setMovieMap(new HashMap<String, List<ProjectionBean>>());

			nodeLocalisation = ((Element) nodeTheater).getElementsByTagName(NODE_LOCALISATION).item(0);
			localisationBean = new LocalisationBean();
			theaterBean.setPlace(localisationBean);
			localisationBean.setSearchQuery(((Element) nodeLocalisation).getAttribute(ATTR_SEARCH_QUERY));

			nodeMovieListTmp = ((Element) nodeTheater).getElementsByTagName(NODE_MOVIE);
			for (int j = 0; j < nodeMovieListTmp.getLength(); j++) {
				nodeMovieTmp = nodeMovieListTmp.item(j);

				projectionBeanList = theaterBean.getMovieMap().get(((Element) nodeMovieTmp).getAttribute(ATTR_ID));
				if (projectionBeanList == null) {
					projectionBeanList = new ArrayList<ProjectionBean>();
					theaterBean.getMovieMap().put(((Element) nodeMovieTmp).getAttribute(ATTR_ID), projectionBeanList);
				}

				nodeProjectionList = ((Element) nodeMovieTmp).getElementsByTagName(NODE_PROJECTION);
				for (int k = 0; k < nodeProjectionList.getLength(); k++) {
					nodeProjectionTmp = nodeProjectionList.item(k);
					projectionBean = new ProjectionBean();
					projectionBean.setShowtime(Long.valueOf(((Element) nodeProjectionTmp).getAttribute(ATTR_TIME)));
					projectionBean.setReservationLink(((Element) nodeProjectionTmp).getAttribute(ATTR_RESERVATION_URL));
					projectionBean.setSubtitle(((Element) nodeProjectionTmp).getAttribute(ATTR_LANG));
					projectionBeanList.add(projectionBean);
				}
			}

		}

		return nearResp;
	}

}
