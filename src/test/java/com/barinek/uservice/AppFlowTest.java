package com.barinek.uservice;

import com.barinek.uservice.support.AppRunner;
import com.barinek.uservice.support.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppFlowTest extends AppRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppFlowTest.class);

    @Test
    public void testBasicFlow() throws Exception {
        TestDataSource.cleanWithFixtures();

        String aUserId = register();
        logger.info("Registered userId => {}", aUserId);

        String user = showUser(aUserId);
        logger.info("Show user => {}", user);

        String anAccountId = showAccount(aUserId);
        logger.info("Created accountId => {}", anAccountId);

        String aProjectId = createProject(anAccountId);
        logger.info("Created projectId => {}", aProjectId);

        String projects = listProjects(anAccountId);
        logger.info("List projects => {}", projects);

        String allocation = createAllocation(aProjectId, aUserId);
        logger.info("Created allocation => {}", allocation);

        String allocations = listAllocations(aProjectId);
        logger.info("List allocations => {}", allocations);

        String entry = createTimeEntry(aProjectId, aUserId);
        logger.info("Created timeEntry => {}", entry);

        String entries = listTimeEntries(aUserId);
        logger.info("Lisa timeEntries => {}", entries);

        String story = createStory(aProjectId);
        logger.info("Created story => {}", story);

        String stories = listStories(aProjectId);
        logger.info("List stories => {}", stories);
    }

    private String register() throws java.io.IOException {
        String response = doPost("http://localhost:8080/registration", "{\"name\": \"aUser\"}");

        Pattern envPattern = Pattern.compile("id\":(\\d+),");
        Matcher matcher = envPattern.matcher(response);
        matcher.find();
        return matcher.group(1);
    }

    private String showUser(String userId) throws java.io.IOException, URISyntaxException {
        return doGet("http://localhost:8080/users", new BasicNameValuePair("userId", userId));
    }

    private String showAccount(String ownerId) throws java.io.IOException, URISyntaxException {
        String response = doGet("http://localhost:8080/accounts", new BasicNameValuePair("ownerId", ownerId));
        Pattern envPattern = Pattern.compile("id\":(\\d+),");
        Matcher matcher = envPattern.matcher(response);
        matcher.find();
        return matcher.group(1);
    }

    private String createProject(String accountId) throws java.io.IOException {
        String response = doPost("http://localhost:8080/projects", String.format("{\"accountId\":\"%s\",\"name\":\"aProject\"}", accountId));

        Pattern envPattern = Pattern.compile("id\":(\\d+),");
        Matcher matcher = envPattern.matcher(response);
        matcher.find();
        return matcher.group(1);
    }

    private String listProjects(String accountId) throws IOException, URISyntaxException {
        return doGet("http://localhost:8080/projects", new BasicNameValuePair("accountId", accountId));
    }

    private String createAllocation(String aProjectId, String aUserId) throws IOException {
        return doPost("http://localhost:8080/allocations", String.format("{\"projectId\":%s,\"userId\":%s,\"firstDay\":\"2015-05-17\",\"lastDay\":\"2015-05-26\"}", aProjectId, aUserId));
    }

    private String listAllocations(String aProjectId) throws IOException, URISyntaxException {
        return doGet("http://localhost:8080/allocations", new BasicNameValuePair("projectId", aProjectId));
    }

    private String createTimeEntry(String aProjectId, String aUserId) throws IOException {
        return doPost("http://localhost:8080/time-entries", String.format("{\"projectId\":%s,\"userId\":%s,\"date\":\"2015-05-17\",\"hours\":\"8\"}", aProjectId, aUserId));
    }

    private String listTimeEntries(String aUserId) throws IOException, URISyntaxException {
        return doGet("http://localhost:8080/time-entries", new BasicNameValuePair("userId", aUserId));
    }

    private String createStory(String aProjectId) throws IOException {
        return doPost("http://localhost:8080/stories", String.format("{\"projectId\":%s,\"name\":\"A story\"}", aProjectId));
    }

    private String listStories(String aProjectId) throws IOException, URISyntaxException {
        return doGet("http://localhost:8080/stories", new BasicNameValuePair("projectId", aProjectId));
    }
}