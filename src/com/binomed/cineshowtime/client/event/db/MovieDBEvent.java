package com.binomed.cineshowtime.client.event.db;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.event.BeanEvent;
import com.binomed.cineshowtime.client.handler.db.MovieDbHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.event.shared.GwtEvent;

public class MovieDBEvent extends BeanEvent<MovieBean, MovieDbHandler> {

	public static GwtEvent.Type<MovieDbHandler> TYPE = new Type<MovieDbHandler>();

	private ArrayList<MovieBean> movieList;

	public MovieDBEvent(ArrayList<MovieBean> movieList) {
		super((MovieBean) null);
		this.movieList = movieList;
	}

	public MovieDBEvent(MovieBean movie) {
		super(movie);
	}

	public MovieDBEvent(Throwable exception) {
		super(exception);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MovieDbHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MovieDbHandler handler) {
		if (getBean() != null) {
			handler.movie(getBean());
		} else if (movieList != null) {
			handler.movieList(movieList);
		} else if (getException() != null) {
			handler.onError((DataServiceException) getException());
		}

	}

}
