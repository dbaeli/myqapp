package com.infoq.myqapp.controller;

import com.infoq.myqapp.domain.FeedEntry;
import com.infoq.myqapp.domain.RequestResult;
import com.infoq.myqapp.service.TrelloAuthenticationService;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/trello")
public class TrelloController {

    private static final Logger LOG = LoggerFactory.getLogger(TrelloController.class);

    private static final String ATTR_OAUTH_REQUEST_TOKEN = "oauthrequestoken";

    private static final String ATTR_OAUTH_ACCESS_TOKEN = "oauthaccesstoken";

    @Resource
    private TrelloAuthenticationService trelloAuthenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/card")
    @ResponseBody
    public RequestResult addToTrello(@RequestBody FeedEntry feed, WebRequest request) throws Exception {
        LOG.info("Adding card to Trello {}", feed.getTitle());

        Token requestToken = (Token) request.getAttribute(ATTR_OAUTH_REQUEST_TOKEN, RequestAttributes.SCOPE_SESSION);
        Token accessToken = (Token) request.getAttribute(ATTR_OAUTH_ACCESS_TOKEN, RequestAttributes.SCOPE_SESSION);

        if (requestToken == null || accessToken == null) {
            return new RequestResult("api/trello/login");
        }
        return new RequestResult("");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(WebRequest request) throws Exception {
        Token requestToken = (Token) request.getAttribute(ATTR_OAUTH_REQUEST_TOKEN, RequestAttributes.SCOPE_SESSION);
        Token accessToken = (Token) request.getAttribute(ATTR_OAUTH_ACCESS_TOKEN, RequestAttributes.SCOPE_SESSION);
        LOG.info("Login attempt with request and access token : {} {}", requestToken, accessToken);
        if (requestToken == null || accessToken == null) {
            // generate new request token
            LOG.debug("1");
            OAuthService service = trelloAuthenticationService.getService();
            LOG.debug("2");
            requestToken = service.getRequestToken();
            LOG.debug("3");
            request.setAttribute(ATTR_OAUTH_REQUEST_TOKEN, requestToken, RequestAttributes.SCOPE_SESSION);
            LOG.debug("4");

            // redirect to trello auth page
            String redirectUrl = "redirect:" + service.getAuthorizationUrl(requestToken);

            LOG.debug("5 : {}", redirectUrl);
            return redirectUrl;
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"/callback"}, method = RequestMethod.GET)
    public ModelAndView callback(@RequestParam(value = "oauth_verifier", required = false) String oauthVerifier, WebRequest request) {

        // getting request tocken
        OAuthService service = trelloAuthenticationService.getService();
        Token requestToken = (Token) request.getAttribute(ATTR_OAUTH_REQUEST_TOKEN, RequestAttributes.SCOPE_SESSION);

        // getting access token
        Verifier verifier = new Verifier(oauthVerifier);
        Token accessToken = service.getAccessToken(requestToken, verifier);
        LOG.info("Access Granted to Trello with token {}", accessToken);

        // store access token as a session attribute
        request.setAttribute(ATTR_OAUTH_ACCESS_TOKEN, accessToken, RequestAttributes.SCOPE_SESSION);

        // getting user profile

        ModelAndView mav = new ModelAndView("redirect:/");
        return mav;
    }
}
