import java.io.*;
import java.net.*;

public class Server {

  //set port using socket
  private static final int PORT = 9999;

  //������ IP:PORT�� ������ ����� socket, bind, listen, accept, send/recv �� ������ �۵��Ѵ�.
  //listen, accept, send, recv�� Ŭ���̾�Ʈ���� ��û�� �ö����� ����ϴ� �����̴�.

  public static void main(String... args){

    //Auto Close. ������ �����Ǹ� ������ ���⶧����, Try-Catch�� ������ �ڵ�����ȴ�.
    //�ٵ� try�� �Ұ�ȣ()�ȿ� �ִ°� ��Ȯ���� ���� �𸣰ڴ�.
    try(ServerSocket server = new ServerSocket()){
      // ���� �ʱ�ȭ
      InetSocketAddress ipep = new InetSocketAddress(PORT);
      server.bind(ipep);

      System.out.println("Initialize complate");

      //LISTEN ���
      //Ŭ���̾�Ʈ�� �����ϸ� socket���¸� ��ȯ. �����Ҷ����� �����.
      Socket client = server.accept();
      InetAddress inetaddr = client.getInetAddress();
      System.out.println( client.getInetAddress() + "���� ����");


      //send,reciever ��Ʈ�� �޾ƿ���
      //�ڵ� close
      try(OutputStream sender = client.getOutputStream();
      InputStream reciever = client.getInputStream();){

        // //Ŭ���̾�Ʈ�� hello world �޽��� ������
        // //11byte ������
        // String message = "hello world";
        // byte[] data = message.getBytes();
        // sender.write(data, 0, data.length);
        //
        // //Ŭ���̾�Ʈ�κ��� �޽��� �ޱ�
        // //2byte ������
        // data = new byte[2];
        // reciever.read(data, 0, data.length);
        //
        // //���� �޽��� ���
        // message = new String(data);
        // String out = String.format("recieve - %s", message);
        // System.out.println(out);

        //��Ʈ���� ���� ���⺸�ٴ�, �Ʒ� ���·� �ְ�޴°� ����.
        //pw�� ������, br�� ������ �ִ�. ��밡 ������ �о���������� �ҿ��� ����.
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(sender));
        BufferedReader br = new BufferedReader(new InputStreamReader(reciever));

        String line = null;
        //null�� ������ �ѹ��� �� ���۰� ������.
        while( (line = br.readLine()) != null){
          System.out.println(line);

          //Ŭ���̾�Ʈ�� ������
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
