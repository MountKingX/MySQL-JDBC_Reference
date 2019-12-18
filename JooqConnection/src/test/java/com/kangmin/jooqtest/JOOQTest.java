package com.kangmin.jooqtest;

import com.kangmin.jooqtest.config.database.TestDSLContextConfig;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.kangmin.jooq.db.generated.Tables.AUTHOR;
import static com.kangmin.jooq.db.generated.Tables.AUTHOR_BOOK;
import static com.kangmin.jooq.db.generated.Tables.BOOK;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = JOOQTest.class)
@Transactional(transactionManager = "testTransactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@Import({TestDSLContextConfig.class})
public class JOOQTest {

    private static final Logger LOG = LoggerFactory.getLogger(JOOQTest.class);

//    private final MockDataProvider provider = new MyProvider();
//    private final MockConnection connection = new MockConnection(provider);
//    private final DSLContext dsl = DSL.using(connection, SQLDialect.MYSQL);

    @Autowired
    @Qualifier("testDSLContext")
    private DSLContext testDSLContext;

    @Test
    public void givenValidData_whenInserting_thenSucceed() {
        LOG.info("Starting JOOQTest - givenValidData_whenInserting_thenSucceed for table {}", AUTHOR.getSchema());
        testDSLContext.insertInto(AUTHOR)
                .set(AUTHOR.ID, 4)
                .set(AUTHOR.FIRST_NAME, "Herbert")
                .set(AUTHOR.LAST_NAME, "Schildt")
                .execute();

        testDSLContext.insertInto(BOOK)
                .set(BOOK.ID, 4)
                .set(BOOK.TITLE, "A Beginner's Guide")
                .execute();

        testDSLContext.insertInto(AUTHOR_BOOK)
                .set(AUTHOR_BOOK.AUTHOR_ID, 4)
                .set(AUTHOR_BOOK.BOOK_ID, 4)
                .execute();

        final Result<Record3<Integer, String, Integer>> result = testDSLContext.select(AUTHOR.ID, AUTHOR.LAST_NAME, DSL.count())
                .from(AUTHOR)
                .join(AUTHOR_BOOK).on(AUTHOR.ID.equal(AUTHOR_BOOK.AUTHOR_ID))
                .join(BOOK).on(AUTHOR_BOOK.BOOK_ID.equal(BOOK.ID))
                .groupBy(AUTHOR.LAST_NAME)
                .orderBy(AUTHOR.LAST_NAME.desc())
                .fetch();

        assertEquals(3, result.size());
        assertEquals("Sierra", result.getValue(0, AUTHOR.LAST_NAME));
        assertEquals(Integer.valueOf(2), result.getValue(0, DSL.count()));
        assertEquals("Bates", result.getValue(2, AUTHOR.LAST_NAME));
        assertEquals(Integer.valueOf(1), result.getValue(2, DSL.count()));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenInserting_thenFail() {
        testDSLContext.insertInto(AUTHOR_BOOK).set(AUTHOR_BOOK.AUTHOR_ID, 4).set(AUTHOR_BOOK.BOOK_ID, 5).execute();
    }

    @Test
    public void givenValidData_whenUpdating_thenSucceed() {
        testDSLContext.update(AUTHOR)
                .set(AUTHOR.LAST_NAME, "Baeldung")
                .where(AUTHOR.ID.equal(3))
                .execute();

        testDSLContext.update(BOOK)
                .set(BOOK.TITLE, "Building your REST API with Spring")
                .where(BOOK.ID.equal(3)).execute();

        testDSLContext.insertInto(AUTHOR_BOOK)
                .set(AUTHOR_BOOK.AUTHOR_ID, 3)
                .set(AUTHOR_BOOK.BOOK_ID, 3)
                .execute();

        final Result<Record3<Integer, String, String>> result = testDSLContext.select(AUTHOR.ID, AUTHOR.LAST_NAME, BOOK.TITLE)
                .from(AUTHOR)
                .join(AUTHOR_BOOK).on(AUTHOR.ID.equal(AUTHOR_BOOK.AUTHOR_ID))
                .join(BOOK).on(AUTHOR_BOOK.BOOK_ID.equal(BOOK.ID))
                .where(AUTHOR.ID.equal(3))
                .fetch();

        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.getValue(0, AUTHOR.ID));
        assertEquals("Baeldung", result.getValue(0, AUTHOR.LAST_NAME));
        assertEquals("Building your REST API with Spring", result.getValue(0, BOOK.TITLE));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenUpdating_thenFail() {
        testDSLContext.update(AUTHOR_BOOK)
                .set(AUTHOR_BOOK.AUTHOR_ID, 4)
                .set(AUTHOR_BOOK.BOOK_ID, 5)
                .execute();
    }

    @Test
    public void givenValidData_whenDeleting_thenSucceed() {
        testDSLContext.delete(AUTHOR)
                .where(AUTHOR.ID.lt(3))
                .execute();

        final Result<Record3<Integer, String, String>> result = testDSLContext.select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .from(AUTHOR)
                .fetch();

        assertEquals(1, result.size());
        assertEquals("Bryan", result.getValue(0, AUTHOR.FIRST_NAME));
        assertEquals("Basham", result.getValue(0, AUTHOR.LAST_NAME));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenDeleting_thenFail() {
        testDSLContext.delete(BOOK)
                .where(BOOK.ID.equal(1))
                .execute();
    }
}