import java.net.*;
import java.io.*;

public class Client {
  private static final int PORT = 9999;

  public static void main(String... args){

    //set port using socket

    //자동 close
    try(Socket client = new Socket()){
      //클라이언트 초기화
      InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", PORT);
      //접속
      client.connect(ipep);
      System.out.println("Connected! type \"quit\" to End");
      //send,reciever 스트림 받아오기
      //자동 close
      OutputStream sender = client.getOutputStream();
      InputStream receiver = client.getInputStream();
      // //서버로부터 데이터 받기
      // //11byte
      // byte[] data = new byte[11];
      // receiver.read(data,0,11);
      //
      // //수신메시지 출력
      // String message = new String(data);
      // String out = String.format("recieve - %s", message);
      // System.out.println(out);
      //
      // //서버로 데이터 보내기
      // //2byte
      // message = "OK";
      // data = message.getBytes();
      // sender.write(data, 0, data.length);

      PrintWriter pw = new PrintWriter(new OutputStreamWriter(sender));
      BufferedReader br = new BufferedReader(new InputStreamReader(receiver));

      //버퍼 리더로 키보드 입력을 간단하게 받아옴.
      BufferedReader keyboard =
      new BufferedReader(new InputStreamReader(System.in));
      String line = null;
      while((line = keyboard.readLine()) != null){
        if(line.equals("quit")) break;

        // 서버로 문자열을 전송.
        pw.println(line);
        pw.flush();

        // // 서버가 다시 반환하는 문자열을 BufferedReader에 있는
        // //    readLine()을 이용해서 읽어들임
        // String echo = br.readLine();
        // System.out.println("서버로부터 전달받은 문자열 :" + echo);
      }

      pw.close();
      br.close();
      client.close();
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
