import java.io.*;
import java.net.*;

public class Server {

  //set port using socket
  private static final int PORT = 9999;

  //서버는 IP:PORT로 소켓을 만들고 socket, bind, listen, accept, send/recv 의 순서로 작동한다.
  //listen, accept, send, recv는 클라이언트에서 요청이 올때까지 대기하는 형태이다.

  public static void main(String... args){

    //Auto Close. 소켓이 단절되면 에러가 나기때문에, Try-Catch로 끝나면 자동종료된다.
    //근데 try의 소괄호()안에 넣는게 정확히는 뭔지 모르겠다.
    try(ServerSocket server = new ServerSocket()){
      // 서버 초기화
      InetSocketAddress ipep = new InetSocketAddress(PORT);
      server.bind(ipep);

      System.out.println("Initialize complate");

      //LISTEN 대기
      //클라이언트가 접속하면 socket형태를 반환. 접속할때까지 대기함.
      Socket client = server.accept();
      InetAddress inetaddr = client.getInetAddress();
      System.out.println( client.getInetAddress() + "에서 접속");


      //send,reciever 스트림 받아오기
      //자동 close
      try(OutputStream sender = client.getOutputStream();
      InputStream reciever = client.getInputStream();){

        // //클라이언트로 hello world 메시지 보내기
        // //11byte 데이터
        // String message = "hello world";
        // byte[] data = message.getBytes();
        // sender.write(data, 0, data.length);
        //
        // //클라이언트로부터 메시지 받기
        // //2byte 데이터
        // data = new byte[2];
        // reciever.read(data, 0, data.length);
        //
        // //수신 메시지 출력
        // message = new String(data);
        // String out = String.format("recieve - %s", message);
        // System.out.println(out);

        //스트림을 직접 쓰기보다는, 아래 형태로 주고받는게 편함.
        //pw로 보내고, br로 받을수 있다. 상대가 보내도 읽어오지않으면 소용이 없다.
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(sender));
        BufferedReader br = new BufferedReader(new InputStreamReader(reciever));

        String line = null;
        //null이 나오면 한번에 온 버퍼가 끝난것.
        while( (line = br.readLine()) != null){
          System.out.println(line);

          //클라이언트로 보내기
          pw.println("server received");
          pw.flush();
        }


        pw.close();
        br.close();
        client.close();
      }
    }catch(Throwable e){
      e.printStackTrace();
    }
  }
}
