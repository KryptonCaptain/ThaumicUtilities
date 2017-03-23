package com.saegusa.thu.utils;

import net.minecraft.util.*;

public class QuadHelper
{
    public double x;
    public double y;
    public double z;
    public double angle;
    
    public QuadHelper(final double ang, final double xx, final double yy, final double zz) {
        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.angle = ang;
    }
    
    public static QuadHelper setAxis(final Vec3 vec, double angle) {
        angle *= 0.5;
        final double d4 = MathHelper.sin((float)angle);
        return new QuadHelper(MathHelper.cos((float)angle), vec.xCoord * d4, vec.yCoord * d4, vec.zCoord * d4);
    }
    
    public void rotate(final Vec3 vec) {
        final double d = -this.x * vec.xCoord - this.y * vec.yCoord - this.z * vec.zCoord;
        final double d2 = this.angle * vec.xCoord + this.y * vec.zCoord - this.z * vec.yCoord;
        final double d3 = this.angle * vec.yCoord - this.x * vec.zCoord + this.z * vec.xCoord;
        final double d4 = this.angle * vec.zCoord + this.x * vec.yCoord - this.y * vec.xCoord;
        vec.xCoord = d2 * this.angle - d * this.x - d3 * this.z + d4 * this.y;
        vec.yCoord = d3 * this.angle - d * this.y + d2 * this.z - d4 * this.x;
        vec.zCoord = d4 * this.angle - d * this.z - d2 * this.y + d3 * this.x;
    }
}
