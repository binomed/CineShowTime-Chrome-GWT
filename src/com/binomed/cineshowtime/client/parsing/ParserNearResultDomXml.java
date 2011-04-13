package com.binomed.cineshowtime.client.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.binomed.cineshowtime.client.model.LocalisationBean;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class ParserNearResultDomXml extends AbstractParser {

	public static NearResp parseResult(String response) {

		NearResp nearResp = new NearResp();

		Document messageDom = XMLParser.parse(response);

		Node nodeMovieList = messageDom.getElementsByTagName(NODE_MOVIE_LIST).item(0);

		NodeList nodeTheaterListTmp = messageDom.getElementsByTagName(NODE_THEATER_LIST);
		Node nodeTheaterList = nodeTheaterListTmp.item(nodeTheaterListTmp.getLength() - 1);

		nearResp.setMapMovies(new HashMap<String, MovieBean>());
		nearResp.setTheaterList(new ArrayList<TheaterBean>());

		NodeList movieList = nodeMovieList.getChildNodes();
		Node nodeMovie = null;
		Node nodeTheaterTmp = null;
		MovieBean movieBean = null;
		NodeList theaterNodeList = null;
		for (int i = 0; i < movieList.getLength(); i++) {
			nodeMovie = movieList.item(i);
			movieBean = new MovieBean();
			movieBean.setState(MovieBean.STATE_NONE);
			movieBean.setId(getString(nodeMovie, ATTR_ID));
			movieBean.setMovieName(getString(nodeMovie, ATTR_MOVIE_NAME));
			movieBean.setEnglishMovieName(getString(nodeMovie, ATTR_ENGLISH_MOVIE_NAME));
			movieBean.setImdbId(getString(nodeMovie, ATTR_IMDB_ID));
			movieBean.setLang(getString(nodeMovie, ATTR_LANG));
			movieBean.setMovieTime(getLong(nodeMovie, ATTR_TIME));
			movieBean.setTheaterList(new ArrayList<String>());

			theaterNodeList = ((Element) nodeMovie).getElementsByTagName(NODE_THEATER);

			for (int j = 0; j < theaterNodeList.getLength(); j++) {
				nodeTheaterTmp = theaterNodeList.item(j);
				movieBean.getTheaterList().add(getString(nodeTheaterTmp, ATTR_ID));
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
			nearResp.getTheaterList().add(theaterBean);
			theaterBean.setId(getString(nodeTheater, ATTR_ID));
			theaterBean.setPhoneNumber(getString(nodeTheater, ATTR_PHONE_NUMBER));
			theaterBean.setTheaterName(getString(nodeTheater, ATTR_THEATER_NAME));
			theaterBean.setMovieMap(new HashMap<String, List<ProjectionBean>>());

			nodeLocalisation = ((Element) nodeTheater).getElementsByTagName(NODE_LOCALISATION).item(0);
			if (nodeLocalisation != null) {
				localisationBean = new LocalisationBean();
				theaterBean.setPlace(localisationBean);
				localisationBean.setSearchQuery(getString(nodeLocalisation, ATTR_SEARCH_QUERY));
			}

			nodeMovieListTmp = ((Element) nodeTheater).getElementsByTagName(NODE_MOVIE);
			for (int j = 0; j < nodeMovieListTmp.getLength(); j++) {
				nodeMovieTmp = nodeMovieListTmp.item(j);

				projectionBeanList = theaterBean.getMovieMap().get(getString(nodeMovieTmp, ATTR_ID));
				if (projectionBeanList == null) {
					projectionBeanList = new ArrayList<ProjectionBean>();
					theaterBean.getMovieMap().put(getString(nodeMovieTmp, ATTR_ID), projectionBeanList);
				}

				nodeProjectionList = ((Element) nodeMovieTmp).getElementsByTagName(NODE_PROJECTION);
				for (int k = 0; k < nodeProjectionList.getLength(); k++) {
					nodeProjectionTmp = nodeProjectionList.item(k);
					projectionBean = new ProjectionBean();
					projectionBean.setShowtime(getLong(nodeProjectionTmp, ATTR_TIME));
					projectionBean.setReservationLink(getString(nodeProjectionTmp, ATTR_RESERVATION_URL));
					projectionBean.setSubtitle(getString(nodeProjectionTmp, ATTR_LANG));
					projectionBeanList.add(projectionBean);
				}
			}

		}

		return nearResp;
	}
}
