'use strict'

let stompClient
let username
let accessTokenGlobal;

const connect = async (event) => {
    event.preventDefault()
    event.stopImmediatePropagation()
    username = document.querySelector('#username').value.trim()

    if (username) {
        const login = document.querySelector('#login')
        login.classList.add('hide')
        document.getElementById('chatWindow').classList.remove('d-none');
        const tokenResponse = await httpPost('http://localhost:8080/api/v1/login', username);
        if (tokenResponse === undefined) {
            console.log("User already exists");
            alert("User with such username already exists. Update page and try again");
            return;
        }
        accessTokenGlobal = 'Bearer ' + tokenResponse;
        console.log('ACCCESS-TOKEN ' + accessTokenGlobal)

        const socketJs = new SockJS('http://localhost:8080/chat')

        const headers = {
            Authorization: accessTokenGlobal,
            passcode: 'test3',
            login: 'test4   ',
            host: 'http://localhost:8080'
        };

        stompClient = webstomp.over(socketJs);
        stompClient.connect(headers, onConnected);
        insertUsers(await giveMeUsers());
    }
}
const insertUsers = (users) => {
    console.log(users);
    users.map(element => {
        document.getElementsByClassName('chat_common')[0].appendChild(createNewElement(element.username));
    })
};

const createNewElement = (userName) => {
    const newElement = document.createElement('div');
    newElement.setAttribute('class', 'chat_member');
    const icon = document.createElement('i');
    icon.setAttribute('class', 'fa-solid fa-hand');
    const text = document.createElement('span');
    text.textContent = userName;
    newElement.appendChild(text);
    newElement.appendChild(icon);
    return newElement;
}

async function httpPost(url, username) {
    const response = await (fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username
        })
    }));
    const json = await response.json();
    console.log(json)
    return json.accessToken;
}

const onConnected = () => {
    stompClient.subscribe('/topic/messages', onMessageReceived)
    const status = document.querySelector('#status')
    status.className = 'hide'
}

const sendMessage = (event, action) => {
    if (stompClient) {
        const chatMessage = {
            author: username,
            messageType: action
        }
        stompClient.send("/app/chat", JSON.stringify(chatMessage))
        console.log('Data sent : ' + JSON.stringify(chatMessage))
    }
}


const onMessageReceived = (payload) => {
    const message = JSON.parse(payload.body);
    console.log("Parsed message : " + message.messageType)

    if (message.messageType === 'HAND_UP') {
        raiseHandUpDown('UP', message.author);
        message.content = message.author + ' raised his hand up!'
    } else if (message.messageType === 'HAND_DOWN') {
        raiseHandUpDown('DOWN', message.author);
        message.content = message.author + ' put his hand down!'
    } else if (message.messageType === 'NEW_USER') {
        userCame(message.author);
        message.content = message.author + ' joined class!'
    } else if (message.messageType === 'LEAVE') {
        userLeave(message.author);
        message.content = message.author + ' left class!'
    } else {
        console.log('Unknown type:(')
    }
}

const userLeave = (userName) => {
    const members = document.getElementsByClassName('chat_member');
    for (let i = 0; i < members.length; ++i) {
        const element = members[i];
        if (element.getElementsByTagName('span')[0].textContent === userName) {
            element.remove();
            return;
        }
    }
};

const userCame = (userName) =>
    document.getElementsByClassName('chat_common')[0].appendChild(createNewElement(userName));

const initiateRequest = (action) => sendMessage(null, action);

function raiseHandUpDown(action, userName) {
    console.log('MI TUT' + userName);
    const members = document.getElementsByClassName('chat_member');
    for (let i = 0; i < members.length; ++i) {
        let element = members[i];
        if (element.getElementsByTagName('span')[0].textContent === userName) {
            const classList = element.getElementsByClassName('fa-hand')[0].classList;
            if (action === 'UP') {
                classList.remove('d-none');
                console.log(userName + "HAND_UP");
            }
            if (action === 'DOWN') {
                classList.add('d-none');
                console.log(userName + "HAND_DOWN");
            }
        }
    }
}

const giveMeUsers = async () => await (await fetch('http://localhost:8080/api/v1/users')).json();

const logout = () => {
    initiateRequest('LEAVE');
    document.location.reload();
}

const loginForm = document.querySelector('#login-form')
loginForm.addEventListener('submit', connect, true)
const messageControls = document.querySelector('#message-controls')
messageControls.addEventListener('submit', sendMessage, true)