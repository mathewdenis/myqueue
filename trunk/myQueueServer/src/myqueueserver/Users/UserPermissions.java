package myqueueserver.Users;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public enum UserPermissions implements Serializable
{

    Root,Read, Write, CreateUsers, DropUsers, CreateQueues, DropQueues
}
