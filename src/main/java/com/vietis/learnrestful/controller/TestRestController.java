package com.vietis.learnrestful.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vietis.learnrestful.model.Answer;
import com.vietis.learnrestful.model.Game;
import com.vietis.learnrestful.model.Player;
import com.vietis.learnrestful.model.Question;

@RestController
public class TestRestController {

	private static HashMap<Integer, ArrayList<Player>> datalistwaitinggame = new HashMap<Integer, ArrayList<Player>>();
	private static HashMap<Integer, ArrayList<Player>> dataListPlayingGame = new HashMap<Integer, ArrayList<Player>>();

	private static ArrayList<Game> listwaitinggame = new ArrayList<Game>();
	private static ArrayList<Game> listplayinggame = new ArrayList<Game>();

	private static int gid_create = 1;
	static {

	}
	/* For creat game, waiting for player to join */

	// api for host to create a game and waiting for player to join, not started yet
	@RequestMapping(value = "/api/create", method = RequestMethod.GET)
	public ResponseEntity<Object> create() {
		boolean check = false;
		int pin = 0;
		// generate pin
		do {
			check = false;
			pin = ThreadLocalRandom.current().nextInt(100000, 999999);
			System.out.println(pin);
			for (Game game : listwaitinggame) {
				if (game.getPin() == pin) {
					check = true;
					break;
				}
			}
		} while (check);
		// create new set of player
		ArrayList<Player> data = new ArrayList<Player>();
		datalistwaitinggame.put(pin, data);
		listwaitinggame.add(new Game(gid_create, pin));
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", 1);
		map.put("game_id", gid_create);
		gid_create++;
		map.put("pin", pin);
		map.put("msg", "New game created, waiting for players");
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	// list all waiting game
	@RequestMapping(value = "/api/listwaitinggame", method = RequestMethod.GET)
	public ResponseEntity<Object> listwaitinggame() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("list", listwaitinggame);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	// view one game and listplayer of a waiting game
	@RequestMapping(value = "api/onewaitinggame/{pin}", method = RequestMethod.GET)
	public ResponseEntity<Object> onewaitinggame(@PathVariable int pin) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int code = 0;
		String msg = "";
		ArrayList<Player> data = datalistwaitinggame.get(pin);
		if (data != null) {
			code = 1;
			msg = "Success";
			map.put("data", data);
		} else {
			msg = "Pin not found";
			map.put("data", null);
		}
		map.put("code", code);
		map.put("msg", msg);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	// for player to join game
	@RequestMapping(value = "/api/join/{username}/{pin}", method = RequestMethod.GET)
	public ResponseEntity<Object> join(@PathVariable String username, @PathVariable int pin) {
		HashMap<Object, Object> mapUser = new HashMap<Object, Object>();

		int code = 0;
		String msg = "";
		ArrayList<Player> data = datalistwaitinggame.get(pin);
		if (data != null) {
			code = 1;
			msg = "Success";
			data.add(new Player(username, 0));
			mapUser.put("data", data);
		} else {
			msg = "Pin not found";
			mapUser.put("data", null);
		}
		mapUser.put("code", code);
		mapUser.put("msg", msg);

		return new ResponseEntity<Object>(mapUser, HttpStatus.OK);
	}
	/* For game started, unable for player to join game */

	// start a game
	@RequestMapping(value="/api/startgame/{pin}",method=RequestMethod.GET)
	public ResponseEntity<Object> startGame(@PathVariable int pin){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		Game startGame;
		Game deleteGame=null;
		int playing_gid=0;
		for (Game game : listwaitinggame) {
			if(pin== game.getPin()) {
				try {
					startGame = (Game)game.clone();
					deleteGame = game;
					playing_gid = game.getId();
					listplayinggame.add(startGame);
					break;
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		ArrayList<Player> listPlayer = datalistwaitinggame.get(pin); 
		listwaitinggame.remove(deleteGame);
		dataListPlayingGame.put(playing_gid, listPlayer);
		map.put("data",listplayinggame );
		return new ResponseEntity<Object>(map,HttpStatus.OK);
	}

	// list all playing game
	@RequestMapping(value = "/api/listplayinggame", method = RequestMethod.GET)
	public ResponseEntity<Object> listplayinggame() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("list", listplayinggame);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	// view one game and listplayer of a playing game
	@RequestMapping(value = "api/oneplayinggame/{gid}", method = RequestMethod.GET)
	public ResponseEntity<Object> oneplayinggame(@PathVariable int gid) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int code = 0;
		String msg = "";
		ArrayList<Player> data = dataListPlayingGame.get(gid);
		if (data != null) {
			code = 1;
			msg = "Success";
			map.put("data", data);
		} else {
			msg = "Pin not found";
			map.put("data", null);
		}
		map.put("code", code);
		map.put("msg", msg);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	// get next question of the playing game
	@RequestMapping(value = "api/next/{gid}/{next}", method = RequestMethod.GET)
	public ResponseEntity<Object> next(@PathVariable int gid, @PathVariable int next) {
		HashMap<Object, Object> mapUser = new HashMap<Object, Object>();
		int code = 0;
		if (next == 1) {
			Question q = new Question(1, "Hôm nay là ngày thứ mấy babe ơi?");
			Answer a1 = new Answer(1, "Thứ 2", q);
			Answer a2 = new Answer(2, "Thứ 5", q);
			Answer a3 = new Answer(3, "Thứ 4", q);
			Answer a4 = new Answer(4, "Thứ 7", q);
			ArrayList<Answer> answers = new ArrayList<Answer>();
			answers.add(a1);
			answers.add(a2);
			answers.add(a3);
			answers.add(a4);
			q.setAnswers(answers);
			mapUser.put("code", 1);
			mapUser.put("msg", "Next");
			HashMap<Object, Object> dataQuestion = new HashMap<Object, Object>();
			dataQuestion.put("qid", q.getqId());
			dataQuestion.put("question", q.getQuestionContent());
			HashMap<Object, Object> dataAnswer = new HashMap<Object, Object>();

			int i = 1;
			for (Answer answer : answers) {

				dataAnswer.put("answerid" + i, answer.getId());
				dataAnswer.put("answer" + i, answer.getContent());
				i++;
			}
			dataQuestion.put("answer", dataAnswer);
			mapUser.put("data", dataQuestion);
		} else {
			mapUser.put("code", 0);
			mapUser.put("msg", "Last Question, No more");
			mapUser.put("data", null);
		}

		return new ResponseEntity<Object>(mapUser, HttpStatus.OK);
	}

	// check answer and show score of all player playing this game
	@RequestMapping(value = "api/answer/{qid}/{ansid}/{username}/{gid}", method = RequestMethod.GET)
	public ResponseEntity<Object> answer(@PathVariable int qid, @PathVariable int ansid, @PathVariable String username,
			@PathVariable int gid) {

		ArrayList<Player> data = dataListPlayingGame.get(gid);
		Player curPlay = new Player();
		for (Player player : data) {
			if (player.getUsername().equals(username)) {
				curPlay = player;
				break;
			}
		}
		HashMap<Object, Object> mapUser = new HashMap<Object, Object>();
		int is_Correct;
		if (ansid == 1) {
			curPlay.setScore(curPlay.getScore() + 1);
			is_Correct = 1;
		} else {
			is_Correct = 0;
		}
		HashMap<Object, Object> current_user = new HashMap<Object, Object>();
		current_user.put("is_Correct", is_Correct);
		current_user.put("correct_ans", curPlay.getScore());
		mapUser.put("data", data);
		mapUser.put("current_user", current_user);

		return new ResponseEntity<Object>(mapUser, HttpStatus.OK);
	}
}
