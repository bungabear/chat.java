import java.net.*;
import java.io.*;

public class Client {
  private static final int PORT = 9999;

  public static void main(String... args){

    //set port using socket

    //�ڵ� close
    try(Socket client = new Socket()){
      //Ŭ���̾�Ʈ �ʱ�ȭ
      InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", PORT);
      //����
      client.connect(ipep);
      System.out.println("Connected! type \"quit\" to End");
      //send,reciever ��Ʈ�� �޾ƿ���
      //�ڵ� close
      OutputStream sender = client.getOutputStream();
      InputStream receiver = client.getInputStream();
      // //�����κ��� ������ �ޱ�
      // //11byte
      // byte[] data = new byte[11];
      // receiver.read(data,0,11);
      //
      // //���Ÿ޽��� ���
      // String message = new String(data);
      // String out = String.format("recieve - %s", message);
      // System.out.println(out);
      //
      // //������ ������ ������
      // //2byte
      // message = "OK";
      // data = message.getBytes();
      // sender.write(data, 0, data.length);

      PrintWriter pw = new PrintWriter(new OutputStreamWriter(sender));
      BufferedReader br = new BufferedReader(new InputStreamReader(receiver));

      //���� ������ Ű���� �Է��� �����ϰ� �޾ƿ�.
      BufferedReader keyboard =
      new BufferedReader(new InputStreamReader(System.in));
      String line = null;
      while((line = keyboard.readLine()) != null){
        if(line.equals("quit")) break;

        // ������ ���ڿ��� ����.
        pw.println(line);
        pw.flush();

        // // ������ �ٽ� ��ȯ�ϴ� ���ڿ��� BufferedReader�� �ִ�
        // //    readLine()�� �̿��ؼ� �о����
        // String echo = br.readLine();
        // System.out.println("�����κ��� ���޹��� ���ڿ� :" + echo);
      }

      pw.close();
      br.close();
      client.close();
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
