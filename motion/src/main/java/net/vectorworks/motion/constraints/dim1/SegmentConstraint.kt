package net.vectorworks.motion.constraints.dim1

import net.vectorworks.motion.constraints.Constraint
import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.geometry.objects.dim1.Segment

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */

class SegmentConstraint(start: Point, end: Point) : Segment(start, end), Constraint