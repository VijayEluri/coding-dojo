var assert = buster.assertions.assert, refute = buster.assertions.refute;

buster.testCase('Bowling Game Tests', {

	setUp: function() {
		this.game = new iterate.BowlingGame();

		this.rollMany = function (pins, times) {
			for (var i = 0; i < times; i++) {
				this.game.roll(pins);
			}
		}
	},

	"test gutter game yields 0 in score": function () {
		this.rollMany(0, 20);

		assert.equals(0, this.game.score());
    }

});
