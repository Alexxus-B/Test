require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: Hello
        intent!: /привет
        a: Привет привет
        
    state: AppNumbers
     q!: $regexp_i<\d{11}>
    
    script:
        // Извлекаем все 11-значные последовательности из текста пользователя
        var applicationNumbers = [];
        var userText = $parseTree.text;
        var regex = \d{11};
        var match;
        
        while ((match = regex.exec(userText)) !== null) {
            applicationNumbers.push(match[0]);
        }
        
        // Если найдены номера заявок
        if (applicationNumbers.length > 0) {
            // Если один номер
            if (applicationNumbers.length === 1) {
                $response = "Ваш номер заявки – " + applicationNumbers[0];
            } 
            // Если несколько номеров
            else {
                var message = "Ваши номера заявок:\n";
                for (var i = 0; i < applicationNumbers.length; i++) {
                    message += "• " + applicationNumbers[i] + "\n";
                }
                $response = message;
            }
        } 
        // Если номер не найден
        else {
            $response = "Пожалуйста, укажите номер заявки";
        }

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: Match
        event!: match
        a: {{$context.intent.answer}}