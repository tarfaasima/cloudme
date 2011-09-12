package org.cloudme.sugar;

import org.junit.Test;

import com.google.inject.Module;

public class AbstractDaoTest extends AbstractServiceTestCase {

	public static class MyEntity extends Entity {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class MyEntityDao extends AbstractDao<MyEntity> {
		public MyEntityDao() {
			super(MyEntity.class);
		}
	}

	@Test
	public void testRegisterObjectifyClass() {
		new MyEntityDao();
		// Attemt to create the DAO twice; but it should be registered only once
		// - so no further exception is expected.
		new MyEntityDao();
	}

	@Override
	protected Module[] getModules() {
		return new Module[] {};
	}

}
