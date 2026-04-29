define(['jquery',
	'underscore',
	'backbone',
	'goatApp/model/FlagsCollection',
	'text!templates/scoreboard.html'],
function($,
	_,
	Backbone,
	FlagsCollection,
	ScoreboardTemplate) {

	// Helper function to sanitize rankings array
	function sanitizeRankings(rankings) {
		function escapeHtml(str) {
			if (typeof str !== 'string') return str;
			return str.replace(/&/g, '&amp;')
				.replace(/</g, '&lt;')
				.replace(/>/g, '&gt;')
				.replace(/"/g, '&quot;')
				.replace(/'/g, '&#39;');
		}
		return rankings.map(function(ranking) {
			var sanitized = {};
			for (var key in ranking) {
				if (ranking.hasOwnProperty(key)) {
					sanitized[key] = escapeHtml(ranking[key]);
				}
			}
			return sanitized;
		});
	}

	return Backbone.View.extend({
		el:'#scoreboard',

		initialize: function() {
		    this.template = ScoreboardTemplate,
		    this.collection = new FlagsCollection();
		    this.listenTo(this.collection,'reset',this.render)
		    this.collection.fetch({reset:true});
		},

		render: function() {
			//this.$el.html('test');
			var t = _.template(this.template);
            this.$el.html(t({'rankings':sanitizeRankings(this.collection.toJSON())})); // where sanitizeRankings applies HTML/entity escaping to all user-controlled properties
            setTimeout(this.pollData.bind(this), 5000);
		},

		pollData: function() {
		    this.collection.fetch({reset:true});
		}
	});
});