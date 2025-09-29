package com.engenharia_software.agenda.interfaces;

public interface IContato {
    public String getTelefone();
    public void ligar(String numeroTelefone);
    public void desligar();
    public void enviarMensagem(String numeroTelefone);
}
