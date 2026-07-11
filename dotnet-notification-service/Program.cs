// Small ASP.NET Core microservice that "sends" notifications
// for the queue management system's Spring Boot app.
// In a real system this would send SMS/email/push - here we
// just log the message and keep it in an in-memory list so
// it's easy to see what happened.

var builder = WebApplication.CreateBuilder(args);

// allow the Spring Boot app (running on a different port) to call us
builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(policy =>
    {
        policy.AllowAnyOrigin()
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});

var app = builder.Build();
app.UseCors();

// keeping sent notifications in memory just so we can list them for demo purposes
var sentNotifications = new List<NotificationRecord>();

app.MapGet("/", () => "Notification microservice is running. POST to /api/notifications to send one.");

// this is the endpoint the Spring Boot app calls when a token is generated
app.MapPost("/api/notifications", (NotificationRequest request) =>
{
    Console.WriteLine($"[NOTIFY] Token {request.TokenNumber} (id={request.TokenId}): {request.Message}");

    var record = new NotificationRecord(
        request.TokenId,
        request.TokenNumber,
        request.Message,
        DateTime.Now
    );

    sentNotifications.Add(record);

    return Results.Ok(new { status = "SENT", sentAt = record.SentAt });
});

// simple endpoint to see everything that has been "sent" so far - handy for demo/debugging
app.MapGet("/api/notifications", () => sentNotifications);

app.Run("http://localhost:5000");


// request coming in from the Spring Boot app
record NotificationRequest(long TokenId, string TokenNumber, string Message);

// what we keep in memory
record NotificationRecord(long TokenId, string TokenNumber, string Message, DateTime SentAt);
