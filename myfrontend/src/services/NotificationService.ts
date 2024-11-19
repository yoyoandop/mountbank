class NotificationService {
    private socket: WebSocket | null = null;
    private heartBeatInterval: any = null;
    private jwtToken: string | null = null;  // 用于存储用户的 JWT token

    // 连接到 WebSocket 服务
    connect() {
        const wsUrl = `wss://localhost:8443/notifications`;  // 与后端配置一致
        this.socket = new WebSocket(wsUrl);

        this.socket.onopen = () => {
            console.log('WebSocket 已连接');
            this.startHeartBeat();  // 连接后启动心跳机制
            this.sendAuthToken();  // 连接时发送 JWT token
        };

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);  // 解析 JSON 消息
            const sender = data.sender;  // 获取发送者的信息
            const message = data.message;  // 获取消息内容

            console.log(`接收到来自 ${sender} 的消息: ${message}`);
            // 调用函数来处理并显示接收到的消息
            this.displayMessage(sender, message);

            // 如果接收到的是心跳响应，可以处理心跳
            if (data.status === 'alive') {
                console.log('收到服务器的心跳响应');
            }
        };

        this.socket.onclose = () => {
            console.log('WebSocket 已断开');
            this.stopHeartBeat();  // 断开时停止心跳机制
            this.startReconnect();  // 启动重连机制
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket 错误:', error);
        };
    }

    // 启动心跳机制
    private startHeartBeat() {
        this.heartBeatInterval = setInterval(() => {
            if (this.socket && this.socket.readyState === WebSocket.OPEN) {
                console.log('发送心跳包');
                this.socket.send('{"type": "heartbeat"}');  // 发送心跳消息
            }
        }, 10 * 60 * 1000);  // 每 10 分钟发送一次心跳包
    }

    // 停止心跳机制
    private stopHeartBeat() {
        if (this.heartBeatInterval) {
            clearInterval(this.heartBeatInterval);
        }
    }

    // 发送 JWT token 用于认证
    private sendAuthToken() {
        if (this.jwtToken && this.socket && this.socket.readyState === WebSocket.OPEN) {
            const authMessage = JSON.stringify({ type: 'auth', token: this.jwtToken });
            this.socket.send(authMessage);
        }
    }

    // 启动重连机制
    private startReconnect() {
        setTimeout(() => {
            console.log('尝试重新连接 WebSocket...');
            this.connect();  // 尝试重新连接
        }, 5 * 1000);  // 每 5 秒尝试重新连接一次
    }

    // 关闭 WebSocket 连接
    disconnect() {
        if (this.socket) {
            this.socket.close();
        }
    }

    // 发送消息给服务器（可选，用于双向通信）
    sendMessage(message: string) {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(message);
        }
    }

    // 显示消息的函数
    private displayMessage(sender: string, message: string) {
        // 你可以根据需求在 UI 中显示这条消息
        // 例如：弹出一个通知或者将其显示在页面上
        alert(`来自 ${sender} 的消息: ${message}`);  // 使用 alert 显示消息（你可以自定义通知样式）
    }
}

export default new NotificationService();
