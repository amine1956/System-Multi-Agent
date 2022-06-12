package agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

public class Servere extends Agent {
    int choix = 0;
    @Override
    protected void setup() {

        int  majico = (int) (Math.random() * 50);
        System.out.println("/////////////////////////////////"+majico+"/////////////////////////////////");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {

                    ACLMessage messageAcl = new ACLMessage(ACLMessage.INFORM);
                    try {
                        choix = Integer.parseInt(aclMessage.getContent());
                        if (choix == majico) { //l'egalite entre la valeur saisie et le nobre majic

                            SearchConstraints searchConstraints = new SearchConstraints();

                            searchConstraints.setMaxResults ( new Long(-1) );
                            AMSAgentDescription [] agents  = AMSService.search(Servere.this, new AMSAgentDescription(), searchConstraints);

                            // difuser le message lorsque  il ya un gangnat
                            for (int i=0; i<agents.length;i++){
                                messageAcl.addReceiver(new AID(agents[i].getName().getLocalName(), AID.ISLOCALNAME));
                            }
                            messageAcl.setContent(aclMessage.getSender().getLocalName()+" C'est le gangant");
                            send(messageAcl);
                            doDelete();
                        } else if (choix > majico) {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("saisire une valeur moins que :"+ choix);
                            send(messageAcl);
                        } else {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("saisire une valeur plus que :"+ choix);
                            send(messageAcl);
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    block();
                }

            }
        });
        System.out.println("*** Agent " + getLocalName() + " started ***");
    }

    @Override
    protected void takeDown() {
        System.out.println("*** Agent " + getLocalName() + " terminated ***");
    }

    @Override
    protected void beforeMove() {
        System.out.println("*** Agent " + getLocalName() + " before move ***");
    }
    @Override
    protected void afterMove() {
        System.out.println("*** Agent " + getLocalName() + " after move ***");
    }

}
