package com.sample.factpedia.core.common.result

import java.io.IOException

open class NetworkException: IOException()

class BadRequestException : NetworkException()
class ConflictException : NetworkException()
class ForbiddenException : NetworkException()
class InternalServerException : NetworkException()
class NotFoundException : NetworkException()
class TimeoutException : NetworkException()
class UnauthorizedException : NetworkException()
class UnavailableException : NetworkException()
class PreconditionFailedException : NetworkException()
class NotExpectedException : Exception()