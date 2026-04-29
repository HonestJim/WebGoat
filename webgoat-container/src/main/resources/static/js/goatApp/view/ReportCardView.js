define(['jquery', 'backbone', 'underscore', 'goatApp/model/ReportCardModel', 'text!templates/report_card.html'],
    function ($, Backbone, _, ReportCardModel, ReportCardTemplate) {
        return Backbone.View.extend({
            el: '#report-card-page',
            template: ReportCardTemplate,

            initialize: function () {
                var _this = this;
                this.model = new ReportCardModel();
                this.model.fetch().then(function() {
                    _this.render();
                });
            },

            render: function () {
                var t = _.template(this.template);
                this.$el.html(t(_.escape(this.model.toJSON()))); // or ensure template uses escaped tags
                return this;
            }
        });
    });