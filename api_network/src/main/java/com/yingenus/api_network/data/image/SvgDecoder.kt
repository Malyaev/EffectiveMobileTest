package com.yingenus.api_network.data.image

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import okio.IOException
import java.io.InputStream

internal class SvgDecoder : ResourceDecoder<InputStream,SVG> {
    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    override fun decode(
        source: InputStream,
        width: Int,
        height: Int,
        options: Options
    ): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source)
            if (width != SIZE_ORIGINAL) {
                svg.documentWidth = width.toFloat()
            }
            if (height != SIZE_ORIGINAL) {
                svg.documentHeight = height.toFloat()
            }
            return SimpleResource(svg)
        } catch ( ex : SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex);
        }
        }
    }
