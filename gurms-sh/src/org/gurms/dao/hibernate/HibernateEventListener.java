package org.gurms.dao.hibernate;

import org.gurms.common.util.SpringUtil;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

public class HibernateEventListener implements PostInsertEventListener,
		PostUpdateEventListener, PostDeleteEventListener {

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		// TODO Auto-generated method stub
		System.out.println("-----------delete-----------------");
		System.out.println(event.getId());
		System.out.println(event.getEntity());
		System.out.println(event.getPersister());
		System.out.println(event.getDeletedState());
		System.out.println("----------------------------");
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		// TODO Auto-generated method stub
		SpringUtil.getApplicationContext();
		System.out.println("-----------update-----------------");
		System.out.println(event.getId());
		System.out.println(event.getEntity());
		System.out.println(event.getPersister());
		System.out.println(event.getState());
		System.out.println(event.getOldState());
		System.out.println("----------------------------");
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		// TODO Auto-generated method stub
		System.out.println("------------insert----------------");
		System.out.println(event.getId());
		System.out.println(event.getEntity());
		System.out.println(event.getPersister());
		System.out.println(event.getState());
		System.out.println("----------------------------");
	}

}
