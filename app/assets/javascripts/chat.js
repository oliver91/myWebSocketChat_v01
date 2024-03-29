$(document).ready(function(){

    function Message(data) {
        this.message = ko.observable(data.message);
        this.color = ko.observable("#8bc5d");
        this.timeStamp = data.timeStamp;
    }

    function ChatMessageListViewModel()
    {
        // Data
        var self = this;
        self.messages = ko.observableArray([]);
        self.showErrorMessage = ko.observable(false);
        self.showDisconnectMessage = ko.observable(false);

        self.save = function() {
            chatSocket.send(JSON.stringify({text: $("#newMessage").val()}));
            $("#newMessage").val('');
        };
        self.check = function(){
            var event = arguments[1];
            if(event.which == 13 && !event.shiftKey){
                event.preventDefault();
                self.save();
            }
        };
        self.add = function(data){
            self.messages.unshift(new Message(data));
        };

        if(typeof WebSocket == "undefined"){
            self.showErrorMessage(true);
            return;
        }
        var chatSocket = new WebSocket(jsRoutes.controllers.Application.chat().webSocketURL('response'));

        chatSocket.onmessage = function(event){
            data = JSON.parse(event.data);
            console.log(data);

            switch(data.kind){
                case "join":
                    self.add(data);
                    break;
                case "talk":
                    //self.update();
                    self.add(data);
                    break;
            }
        };
    }

    ko.applyBindings(new ChatMessageListViewModel());
});



